import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class urlReaderGUI extends JFrame{
    private JTextField input;
    private JButton downloadButton;
    private JEditorPane result;
    private JLabel label;
    private JPanel mainPanel;

    public urlReaderGUI(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread T1 = new Thread(disable);
                Thread T2 = new Thread(getURL);

                T1.start();
                T2.start();

            }
        });
    }

    public Runnable getURL = new Runnable(){
        @Override
        public void run() {
            downloadButton.setEnabled(false);
            System.out.print("SetDisable");
            String link = input.getText();
            System.out.println(link);

            synchronized(this) {
                try{
                    URL url=new URL("https://"+link);
                    URLConnection urlConnection = url.openConnection();
                    System.out.println("Host Name: "+url.getHost()); // Using getHost() method
                    System.out.println("Content: "+ url.getContent());

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String urlString = "";
                    String current;

                    while((current = bufferedReader.readLine()) != null)
                    {
                        urlString += current;
                    }
                    System.out.println(urlString);
                    result.setText(urlString);
                    bufferedReader.close();

                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                notify();
                }


//
//            try{
//                URL url=new URL("https://"+link);
//                URLConnection urlConnection = url.openConnection();
//                System.out.println("Host Name: "+url.getHost()); // Using getHost() method
//                System.out.println("Content: "+ url.getContent());
//
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//                String urlString = "";
//                String current;
//
//                while((current = bufferedReader.readLine()) != null)
//                {
//                    urlString += current;
//                }
//                System.out.println(urlString);
//                result.setText(urlString);
//                bufferedReader.close();
//
//            }
//            catch(Exception e)
//            {
//                e.printStackTrace();
//            }

            System.out.print("getURL end");

        }
    };

    Thread disable = new Thread(){
        @Override
        public void run() {
//            try {
//                getURL.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }


            synchronized(getURL) {
                try {
                    getURL.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.print("SetEnable");
            downloadButton.setEnabled(true);


        }
    };

    public static void main(String[] args) {
        JFrame frame = new urlReaderGUI("URL DOWNLOAD (A10909001)");
        frame.setSize(800, 600);
        frame.setVisible(true);



    }



}

