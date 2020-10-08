package ru.geekbrains.OnlineChat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientGui extends JFrame implements ActionListener, Thread.UncaughtExceptionHandler {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    private final JTextArea log = new JTextArea();
    private final JPanel panelTop = new JPanel(new GridLayout(2, 3));

    private final JTextField tfIPAddress = new JTextField("127.0.0.1");
    private final JTextField tfPort = new JTextField("8189");
    private final JCheckBox cbAlwaysOnTop = new JCheckBox("Always on top");
    private final JTextField tfLogin = new JTextField("ivan");
    private final JPasswordField tfPassword = new JPasswordField("123");
    private final JButton btnLogin = new JButton("Login");

    private final JPanel panelBottom = new JPanel(new BorderLayout());
    private final JButton btnDisconnect = new JButton("<html><b>Disconnect</b></html>");
    private final JTextField tfMessage = new JTextField();
    private final JButton btnSend = new JButton("Send");

    private final JList<String> userList = new JList<>();

/*Thread readerThread = new Thread( new IncomingReader());
    readerThread.star();

 */

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() { // Event Dispatching Thread
                new ClientGui();
            }
        });
    }

    private ClientGui() {
        Thread.setDefaultUncaughtExceptionHandler(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(WIDTH, HEIGHT);
        JScrollPane scrollLog = new JScrollPane(log);
        JScrollPane scrollUser = new JScrollPane(userList);
        String[] users = {"user1", "user2", "user3", "user4", "user5",
                "user_with_an_exceptionally_long_name_in_this_chat"};
        userList.setListData(users);
        log.setEditable(false);
        scrollUser.setPreferredSize(new Dimension(150, 0));
        cbAlwaysOnTop.addActionListener(this);

        panelTop.add(tfIPAddress);
        panelTop.add(tfPort);
        panelTop.add(cbAlwaysOnTop);
        panelTop.add(tfLogin);
        panelTop.add(tfPassword);
        panelTop.add(btnLogin);
        panelBottom.add(btnDisconnect, BorderLayout.WEST);
        panelBottom.add(tfMessage, BorderLayout.CENTER);
        panelBottom.add(btnSend, BorderLayout.EAST);

       /* Thread readerThread = new Thread( new IncomingReader());
    readerThread.start();   Новый поток со вложенным класом , чтение данных с сервера и вывод
    сообщений в прокручиваемую текстовую область


        */


        add(scrollLog, BorderLayout.CENTER);
        add(scrollUser, BorderLayout.EAST);
        add(panelTop, BorderLayout.NORTH);
        add(panelBottom, BorderLayout.SOUTH);
        setVisible(true);
    }
    /* Не понимаю момента , нам ведь нужно использовать сокет где-то , чтобы получать и отдавать
        потоки , и чтобы сообщения от сервера получать тоже
        Чтобы отправлять сообщения на сервер  и получать  :
        private void setUpNet () {
        try {
        sock = new Socket (" на сколько я понимаю сюда надо вставить  ID компа ", 8000);
        InputStreamReader stream = new InputStreamReader( sock.getInputStream());
        reader = new BufferReader (streamReader);
        writer = new PrintWriter ( sock.getOutputStream());
        }
        catch (IOExeption ex){
        ex.printStackTrace();
        }
        Вот когда пользователь нажимает кнопку Send   , то сообщение отправляется на сервер
        }
        public class SendButtonL implements ActionListener{
        public void action( ActionEvent ev){
        try{
        writer.println(outgoing.getText()):
        writer.flush();
        }
        catch(Exeption ex) {
        ex.printStackTrace();
        }
        outgoing.setText("");
        outgoing.requestFocus ();
        }
        }
        выше я сделала новый поток и там вложенный класс
        public class IncomingReader implements Runnable {
        public void run ()
        {
        String massage ;
        try{
        while ( ( massage = reader.readLine() ) != null) {
        System.out.println( " " + massage);
        incoming.appened(massage + "\n");
        }
        }
        catch (Exeption ex){ ex.printStackTrace();}
        }
        }

         */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == cbAlwaysOnTop) {
            setAlwaysOnTop(cbAlwaysOnTop.isSelected());
        } else {
            throw new RuntimeException("Unknown source: " + src);
        }
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
        String msg;
        StackTraceElement[] ste = e.getStackTrace();
        msg = "Exception in " + t.getName() + " " +
                e.getClass().getCanonicalName() + ": " +
                e.getMessage() + "\n\t at " + ste[0];
        JOptionPane.showMessageDialog(this, msg, "Exception", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }

}
