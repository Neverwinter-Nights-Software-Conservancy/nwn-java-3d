package net.sf.nwn.viewer;


import java.applet.Applet;
import java.awt.HeadlessException;
import javax.swing.*;
import net.sf.nwn.loader.EmitterBehavior;


public class MainApplet extends Applet
{
    private Display dis;

    public void init()
    {
        String param = getParameter("models");
        String[] names = param.split(";");

        dis = new Display();
        AnimPanel anim = new AnimPanel();
        ControlPanel control = new ControlPanel(dis, anim);
        FilePanel fp = new FilePanel(dis, anim, control, getDocumentBase(), names);

        JFrame jf = new JFrame("NWN Display");
        JDialog fd = new JDialog(jf, "NWN Models");
        JDialog ad = new JDialog(jf, "NWN Animation");
        JDialog cd = new JDialog(jf, "NWN Control Panel");

        fd.getContentPane().add(fp);
        fd.setSize(200, 600);
        fd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        fd.setVisible(true);

        jf.getContentPane().add(dis);
        jf.setSize(600, 600);
        jf.setLocation(fd.getWidth(), 0);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.setVisible(true);

        ad.getContentPane().add(anim);
        ad.setSize(200, 600);
        ad.setLocation(jf.getX() + jf.getWidth(), 0);
        ad.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        ad.setVisible(true);

        cd.getContentPane().add(control);
        cd.setLocation(0, 600);
        cd.setSize(fd.getWidth() + jf.getWidth() + ad.getWidth(), 200);
        cd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        cd.setVisible(true);
    }

    public void destroy()
    {
        dis.destoryEverything();
    }

}
