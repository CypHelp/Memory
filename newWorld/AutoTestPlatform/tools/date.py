import time


class DateTime:

    @classmethod
    def current_time(cls, partten="%H:%M:%S"):
        local = time.localtime(time.time())
        return time.strftime(partten, local)

    @classmethod
    def current_date(cls):
        return cls.current_time(partten="%Y-%m-%d")

    @classmethod
    def current_date_time(cls):
        return cls.current_time(partten="%Y-%m-%d %H:%M:%S")

    @classmethod
    def time_it(cls, func, *args, **kwargs):
        start_time = time.time()
        func(*args, **kwargs)
        end_time = time.time()
        return (end_time - start_time) / 1000


if __name__ == '__main__':
    print(DateTime.current_time())
    print(DateTime.current_date())
    print(DateTime.current_date_time())
