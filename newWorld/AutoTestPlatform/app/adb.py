import os
import re

from appium import webdriver

from AutoTestPlatform.common.exceptions import CommandError, AdbNoStartError, NoSuchProcessNameError
from AutoTestPlatform.tools.file import FileUtil


class Devices:

    def __init__(self, status):
        self.__serial_no, self.__status = status

    def __str__(self):
        return "serial_no:{0},status:{1}".format(self.__serial_no, self.__status)

    def can_user(self):
        return self.__status == "device"


class StartTime:
    ThisTime = "ThisTime"
    TotalTime = "TotalTime"
    WaitTime = "WaitTime"


class Battery:
    AC_Powered = "ACpowered"
    USB_Powered = "USBpowered"
    Wireless_Powered = "Wirelesspowered"
    Status = "status"
    Health = "health"
    Present = "present"
    Level = "level"
    Scale = "scale"
    Voltage = "voltage"
    Temperature = "temperature"
    Technology = "technology"
    Non_charged = "1"
    Charged = "2"


class AndroidDebugBridge:

    @classmethod
    def execute_script(cls, command: str) -> str:
        return os.popen(command).read()

    @classmethod
    def get_connect_devices(cls,path) -> list:
        """
        连接 本地设备
        :param path: 如夜神模拟器 127.0.0.1:62001
        :return:
        """
        result = cls.execute_script("adb connect {} ".format(path))
        if "*" in result:
            raise AdbNoStartError("Adb服务没有启动")
        lines = result.split("\n")
        devices = []
        for line in lines:
            if line.startswith("List of"):
                continue
            c = re.findall("(.+) (device|offline|unknown)", line)
            if len(c) != 0:
                devices.append(Devices(c[0]))
        return devices

    @classmethod
    def get_devices(cls) -> list:
        """
        获取所有设备信息，包含链接的和未连接的
        :return:
        """

        #         res = """List of devices attached
        # 127.0.0.1:62001 device
        # fdsafsd12312 device
        # 127.0.0.1:62001 offline
        # 127.0.0.1:62001 unknown
        #         """
        #         res = """List of devices attached
        # * daemon not running; starting now at tcp:5037
        # * daemon started successfully
        # """

        result = cls.execute_script("adb devices")
        if "*" in result:
            raise AdbNoStartError("Adb服务没有启动")
        lines = result.split("\n")
        devices = []
        for line in lines:
            if line.startswith("List of"):
                continue
            c = re.findall("(.+) (device|offline|unknown)", line)
            if len(c) != 0:
                devices.append(Devices(c[0]))
        return devices

    @classmethod
    def execute_script_on_different_os(cls, win_command="", linux_command="") -> str:
        """
        根据不同的系统执行不同的指令
        :param win_command: windows 指令
        :param linux_command: linux 指令
        :return:
        """
        current_os = FileUtil.get_current_os().lower()
        if current_os == "windows":
            if win_command == "":
                raise CommandError("windows command empty")
            result = cls.execute_script(win_command)
        else:
            if linux_command == "":
                raise CommandError("linux command empty")
            result = cls.execute_script(linux_command)
        return result

    @classmethod
    def get_pid_process_name(cls, name: str) -> list:
        """
        获取当前包的pid（进程号）
        :param name:包名
        :return:
        """
        result = cls.execute_script_on_different_os(win_command='adb shell ps | find "{}"'.format(name),
                                                    linux_command='adb shell ps | grep {}'.format(name))
        # result ="""
        # u0_a35    2912  1466  1219472 59200 ffffffff b742c355 S com.chinat2t32275yuneb.templte:bdservice_v1
        # u0_a35    3308  1466  1347452 174268 ffffffff b742c355 S com.chinat2t32275yuneb.templte
        # """
        cls.check_result(result)
        lines = result.split("\n")
        if len(lines) == 0:
            raise NoSuchProcessNameError("进程名【{}】没有找到".format(name))
        return [re.findall(" {4}(.+?) {1}.+[SR](.+)", line)[0] for line in lines if line != ""]

    @classmethod
    def check_result(cls, result: any, msg="Adb服务没有启动", exception=None) -> None:
        """
        检查结果
        :param result:  需要检查的结果
        :param msg: 检查结果失败后的提示信息
        :param exception:  抛出的异常信息
        :return:
        """
        if not exception:
            exception = AdbNoStartError
        if len(result) == 0:
            raise exception(msg)

    @classmethod
    def get_mac_address(cls) -> str:
        """
        获取Mac地址
        :return:
        """
        return cls.execute_script("adb shell  cat /sys/class/net/wlan0/address")

    @classmethod
    def device_model(cls) -> str:
        """
        获取设备型号
        :return:
        """
        return cls.execute_script("adb shell getprop ro.product.model")

    @classmethod
    def device_android_version(cls) -> str:
        return cls.execute_script("adb shell getprop ro.build.version.release")

    @classmethod
    def device_size(cls) -> str:
        """
        获取屏幕尺寸
        :return:
        """
        return cls.execute_script("adb shell wm size")

    @classmethod
    def device_density(cls) -> str:
        return cls.execute_script("adb shell wm density")

    @classmethod
    def adb_version(cls) -> str:
        """
        获取ADB的版本信息
        :return:
        """
        return cls.execute_script("adb version")

    @classmethod
    def get_all_packages(cls) -> list:
        """
        获取所有的包包含系统的和第三方的
        :return:
        """
        return cls.execute_script("adb shell pm list packages").split("\n")

    @classmethod
    def get_all_system_packages(cls) -> list:
        """
        获取所有包系统的包
        :return:
        """
        return cls.execute_script("adb shell pm list packages -s").split("\n")

    @classmethod
    def get_all_third_packages(cls) -> list:
        """
        获取所有包第三方的包
        :return:
        """
        return cls.execute_script("adb shell pm list packages -3").split("\n")

    @classmethod
    def clear_package_data(cls, package_name) -> None:
        cls.execute_script("adb shell pm clear {}".format(package_name))

    @classmethod
    def start_app(cls, package_name, activity_name) -> None:
        """
        启动app
        :param package_name:  包名
        :param activity_name:  app的activity_name
        :return:
        """
        cls.execute_script("adb shell am start -n {0}/{1}".format(package_name, activity_name))

    @classmethod
    def stop_app(cls, package_name) -> None:
        """
        冷停止，关闭当前app
        :param package_name: 包名
        :return:
        """
        cls.execute_script("adb shell am force-stop {}".format(package_name))

    @classmethod
    def return_desktop(cls) -> None:
        """
        热停止，返回桌面
        :return:
        """
        cls.execute_script("adb shell input keyevent 3")

    @classmethod
    def get_package_activity(cls) -> str:
        """
        获取当前的包的activity
        :return:
        """
        result = cls.execute_script_on_different_os(win_command='adb logcat -d | find "START"',
                                                    linux_command="adb logcat -d | grep START")
        cls.check_result(result)
        lines = result.split("\n")
        line = lines[len(lines) - 1]
        # line =  "I/ActivityManager( 1748): START u0 {cmp=com.chinat2t32275yuneb.templte/com.chinat2t.tp005.activity.MainActivity} from uid 10035 on display 0"
        return re.findall("{cmp=(.+?)/(.+?)}", line)[0]

    @classmethod
    def get_cpu_info(cls, package_name):
        """
        获取CPU占用量
        :param package_name: 包名
        :return:
        """
        result = cls.execute_script_on_different_os(
            win_command='adb shell dumpsys cpuinfo | find "{}"'.format(package_name),
            linux_command="adb shell dumpsys cpuinfo | grep {}".format(package_name))
        cls.check_result(result)
        return re.findall("(.+?)%.+", result)[0].replace(" ", "")

    @classmethod
    def get_network_flow(cls, package_name: str, number: int, network_card="lo") -> tuple:
        """
        获取网络流量
        :param package_name:  包名
        :param number:  第几个进程 获取进程时可能会获得相同包名的不同进程
        :param network_card: 网卡
        :return:
        """
        all_pid = cls.get_pid_process_name(package_name)

        cls.check_result(all_pid)
        if len(all_pid) == 1:
            pid, name = all_pid[0]
        else:
            pid, name = all_pid[number]
        result = cls.execute_script("adb shell cat /proc/{}/net/dev".format(pid))

        # result=cls.execute_script('adb shell cat /proc/{}/net/dev'.format(all_pid))
        cls.check_result(result)
        #         result = """
        # Inter-|   Receive                                                |  Transmit
        #  face |bytes    packets errs drop fifo frame compressed multicast|bytes    packets errs drop fifo colls carrier compressed
        #  wlan0:  404977    6593    0    0    0     0          0         0  3084191    1858    0    0    0     0       0          0
        #   sit0:       0       0    0    0    0     0          0         0        0       0    0    0    0     0       0          0
        #     lo: 6776398   81577    0    0    0     0          0         0  6776398   81577    0    0    0     0       0          0
        #   eth1: 1577704    1955    0    0    0     0          0         0   139674    1658    0    0    0     0       0          0
        # ip6tnl0:       0       0    0    0    0     0          0         0        0       0    0    0    0     0       0          0
        #         """
        lines = result.split("\n")
        current_line = ""
        for line in lines:
            if network_card in line:
                current_line = line
        cls.check_result(current_line, msg="没有找到该网卡{}".format(network_card), exception=ValueError)
        current_line = current_line[current_line.index(":") + 1:]
        all_number = re.findall("(\d+)", current_line)
        return int(all_number[0]), int(all_number[8])

    @classmethod
    def get_device_battery_info(cls, info: str) -> str:
        """
        获取电池信息
        :param info:  Battery类
        :return:
        """
        result = cls.execute_script("adb shell dumpsys battery")
        #         result = """Current Battery Service state:
        # (UPDATES STOPPED -- use 'reset' to restart)
        #   AC powered: false
        #   USB powered: true
        #   Wireless powered: false
        #   status: 5
        #   health: 2
        #   present: true
        #   level: 90
        #   scale: 100
        #   voltage: 10000
        #   temperature: 367
        #   technology: Li-ion
        #                 """
        cls.check_result(result)
        res = re.findall("(.+): (.+)", result)
        res = {k.replace(" ", ""): v for k, v in res}
        return res[info]

    @classmethod
    def get_device_battery(cls) -> int:
        """
        获取设备电量
        :return:
        """
        return int(cls.get_device_battery_info(Battery.Level))

    @classmethod
    def change_charging_status(cls, status: str) -> None:
        """
        改变充电状态
        :param status: 指定的状态
        :return:
        """
        cls.execute_script("adb shell dumpsys battery set status {}".format(status))

    @classmethod
    def get_app_start_time(cls, app_name: str, activity: str, time="WaitTime") -> int:
        """
        获取App启动时间
        :param app_name:  App名
        :param activity:  App的Activity
        :param time:  获取时间的类型 WaitTime  TotalTime ThisTime
        :return: 返回启动时间单位毫秒
        """
        result = cls.execute_script("adb shell am start -W -n {0}/{1}".format(app_name, activity))
        cls.check_result(result)
        #         result = """Starting: Intent { cmp=com.chinat2t32275yuneb.templte/com.chinat2t.tp005.activity.SplashActivity }
        # Status: ok
        # Activity: com.chinat2t32275yuneb.templte/com.chinat2t.tp005.activity.SplashActivity
        # ThisTime: 720
        # TotalTime: 720
        # WaitTime: 735
        # Complete
        #         """
        times = re.findall("(.+): (.+)", result)
        res = {k.replace(" ", ""): v for k, v in times}
        return int(res[time])

    @classmethod
    def stop_server(cls):
        cls.execute_script("adb kill-server")