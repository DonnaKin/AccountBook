package accountBook;
/*
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
 
public class AccountBook_GUI extends JFrame {
    JScrollPane scrollPane;
    ImageIcon icon;
 
    public AccountBook_GUI() {
        icon = new ImageIcon("../../FirstScrren.jpg");
       
        //��� Panel ������ �������������� ����      
        JPanel background = new JPanel() {
            public void paintComponent(Graphics g) {
                g.drawImage(icon.getImage(), 0, 0, null);
                setOpaque(false); //�׸��� ǥ���ϰ� ����,�����ϰ� ����
                super.paintComponent(g);
            }
        };
        scrollPane = new JScrollPane(background);
        setContentPane(scrollPane);
    }
 
    public static void main(String[] args) {
        AccountBook_GUI frame = new AccountBook_GUI();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setVisible(true);
    }
}*/
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class AccountBook_GUI {
 public static void main(String[] args) {
  // TODO Auto-generated method stub
  JFrame frm = new JFrame("�׸� ���� ����");
     ImageIcon ic  = new ImageIcon("FirstScreen.jpg");
     JLabel lbImage1  = new JLabel(ic);
    
     frm.add(lbImage1);
     frm.setVisible(true);
     frm.setBounds(10, 10, 1000, 700);
     frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 }
}