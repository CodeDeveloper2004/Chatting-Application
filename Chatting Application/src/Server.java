import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.net.*;

public class Server implements ActionListener {
    JTextField text;
    JPanel p2;
    static JFrame frame=new JFrame();
    //ve have to align msges vertically
    static Box vertical= Box.createVerticalBox();
    static DataOutputStream dout;

    public Server(){
        frame.getContentPane().setBackground(Color.white);
        //getContentPane means all frame
        //panel
        JPanel p1=new JPanel();
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0,0,450,70);
        p1.setLayout(null);
        frame.add(p1);

        //for image
        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("./Arrow.png"));
        //to only show arrow without background we have to scale the image
        Image i2=i1.getImage().getScaledInstance(25, 25,Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);
        JLabel back =new JLabel(i3);
        back.setBounds(5,20,25,25);
        //we add image at the panel
        p1.add(back);

        //add image for profile
        ImageIcon i4=new ImageIcon(ClassLoader.getSystemResource("./Nakul.jpg"));
        Image i5=i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6=new ImageIcon(i5);
        JLabel profile=new JLabel(i6);
        profile.setBounds(40,10,50,50);
        p1.add(profile);

        //add image for vediocall
        ImageIcon i7=new ImageIcon(ClassLoader.getSystemResource("./video.png"));
        Image i8=i7.getImage().getScaledInstance(35, 35, Image.SCALE_DEFAULT);
        ImageIcon i9=new ImageIcon(i8);
        JLabel video =new JLabel(i9);
        video.setBounds(300,15,35,35);
        p1.add(video);

        //add phone image
        ImageIcon i10=new ImageIcon(ClassLoader.getSystemResource("./phone.png"));
        Image i11=i10.getImage().getScaledInstance(35, 35, Image.SCALE_DEFAULT);
        ImageIcon i12=new ImageIcon(i11);
        JLabel phone=new JLabel(i12);
        phone.setBounds(360,20,35,25);
        p1.add(phone);

        //add dot image
        //add phone image or morevert
        ImageIcon i13=new ImageIcon(ClassLoader.getSystemResource("./Setting.png"));
        Image i14=i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon i15=new ImageIcon(i14);
        JLabel Setting=new JLabel(i15);
        Setting.setBounds(420,20,10,25);
        p1.add(Setting);
        
        //user name
        JLabel name=new JLabel("Nakul");
        name.setBounds(110,20,100,20);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("Arial",Font.BOLD,20));
        p1.add(name);

        //status active or inactive
        JLabel status=new JLabel("Active Now");
        status.setBounds(110,40,100,20);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("Arial",Font.BOLD,13));
        p1.add(status);

        //another panel for text
        p2=new JPanel();
        p2.setBounds(5,75,440,540);
        frame.add(p2);

        //textfield for writing
        text=new JTextField();
        text.setBounds(5,620,310,35);
        text.setFont(new Font("Arial",Font.BOLD,16));
        frame.add(text);

        //button
        JButton send=new JButton("Send");
        send.setBounds(325,620,100,35);
        send.setBackground(new Color(7,94,84));
        send.setForeground(Color.white);
        send.setFont(new Font("Arial",Font.BOLD,16));
        frame.add(send);
        //add functionality to send button
        send.addActionListener(this);


        //add action of mouselistener on arrow
        back.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                System.exit(0);
            }
        });
        frame.setTitle("Chatter");
        frame.setResizable(false);
        frame.setSize(450,700);
        frame.setLayout(null);
        frame.setLocation(200,0);
        frame.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        try{
            String out=text.getText();
            JPanel p3=formatLabel(out);
    
            p2.setLayout(new BorderLayout());
            JPanel right=new JPanel(new BorderLayout());
            //words will end with line end
            right.add(p3,BorderLayout.LINE_END);
            vertical.add(right);
            //space between two lines
            vertical.add(Box.createVerticalStrut(15));
            p2.add(vertical,BorderLayout.PAGE_START);
            dout.writeUTF(out);
            //set textfield empty after sending the message
            text.setText("");
            frame.invalidate();
            frame.validate();
            frame.repaint();
        }
        catch(Exception ae){
            ae.printStackTrace();
        }
    }

    public static JPanel formatLabel(String out){
        JPanel panel=new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output=new JLabel("<html><p style=\"width:150px\">"+out+"</p></html>");
        output.setFont(new Font("Arial",Font.PLAIN,16));
        output.setBackground(new Color(11,211,84));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));
        panel.add(output);
        //to show real time for th
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
        JLabel time =new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);
        return panel;
    }

    public static void main(String[] args) throws Exception {
        new Server();//anonymous object
        try{
            ServerSocket skt=new ServerSocket(6001);//port number
            while(true){
                Socket s=skt.accept();
                DataInputStream din =new DataInputStream(s.getInputStream());
                dout=new DataOutputStream(s.getOutputStream());
                while(true){
                    String msg=din.readUTF();//read msg of client
                    //to display received mes from client we create panel
                    JPanel panel=formatLabel(msg);
                    JPanel left=new JPanel(new BorderLayout());
                    left.add(panel,BorderLayout.LINE_START);
                    //here vertical and validate both are nonstatic variables so
                    //to access these variables we have to make them static
                    vertical.add(left);
                    frame.validate();

                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
