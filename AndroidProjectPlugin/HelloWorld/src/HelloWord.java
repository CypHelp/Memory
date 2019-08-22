import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.ui.JBColor;

import java.awt.*;

/**
 * Author Yp_Love
 * Date 2019-04-17-17-41
 * Descriptation:
 */
public class HelloWord extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {


        // 在光标位置弹出一个窗口  显示HelloWorld 显示几秒

        // 获取光标的位置的 Editor
        Editor editor = e.getData(PlatformDataKeys.EDITOR);

        if(null == editor){
            return;
        }

        // 显示一个popu
        showPopupBalloon(editor,"HelloWorld",4);

    }

    /**
     * 显示dialog
     *
     * @param editor
     * @param result 内容
     * @param time   显示时间，单位秒
     */
    public static void showPopupBalloon(final Editor editor, final String result, final int time) {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            public void run() {
                JBPopupFactory factory = JBPopupFactory.getInstance();// Java GUI
                factory.createHtmlTextBalloonBuilder(result, null, new JBColor(new Color(116, 214, 238), new Color(76, 112, 117)), null)
                        .setFadeoutTime(time * 1000)
                        .createBalloon()
                        .show(factory.guessBestPopupLocation(editor), Balloon.Position.below);
            }
        });
    }

}
