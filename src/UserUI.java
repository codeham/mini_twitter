import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserUI extends JFrame implements UIBuild{
    // Jframe main
    JFrame frame;

    // Admin Panel Instance
    AdminPanel adminPanel;

    // top list panel
    JList topPanel;

    // bottom list panel
    JList bottomPanel;

    // JPane
    JScrollPane topPane;
    JScrollPane bottomPane;

    // list view
    private JTree currentFollowing;
    private JTree newsFeed;

    private JButton followUser;
    private JButton postTweet;

    // text areas
    private JTextField userId;
    private JTextField twitterMessage;

    // user
    User user;

    UserUI(){
        UIbuilder();
    }

    UserUI(User user){
        this.user = user;
        System.out.println("User ID opened is: " + user.getUserId());
        adminPanel = AdminPanel.getInstance();
        UIbuilder();
    }

    private void UIbuilder(){
        textManager();
        listManager();
        buttonManager();
        frameManager();
    }

    public void frameManager(){
        frame = new JFrame();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle("@" + user.getUserId());
        frame.setResizable(false);
        frame.setLayout(null);

        frame.add(userId);
        frame.add(followUser);

        // top text panel
        frame.add(topPanel);

        // bottom text panel
        frame.add(bottomPanel);

        // twitter message
        frame.add(twitterMessage);
        frame.add(postTweet);

        // frame dimensions
        frame.setSize(505,640);
        frame.setVisible(true);
    }

    public void listManager(){
        topPanel = new JList();
        topPanel.setName("Current Following");
        topPanel.setBounds(5,45,495,275);

        bottomPanel = new JList();
        bottomPanel.setName("News Feed");
        bottomPanel.setBounds(5,370,495,245);

    }

    public void treeManager(){}

    public void textManager(){
        userId = new JTextField();
        userId.setBounds(5,5,250, 35);

        twitterMessage = new JTextField();
        twitterMessage.setBounds(5, 325, 250, 35);
    }

    public void labelManager(){

    }

    public void buttonManager(){
        followUser = new JButton();
        followUser.setText("Follow User");
        followUser.setBounds(255, 5, 245, 35);
        followUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                followUser();
            }
        });

        postTweet = new JButton();
        postTweet.setText("Post Tweet");
        postTweet.setBounds(255, 325, 245, 35);
        postTweet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                postUserTweet();
            }
        });
    }

    public void postUserTweet(){
        String message = twitterMessage.getText();
        this.user.tweetMessage(message);
        twitterMessage.setText("");
        // observer notified by User object...
    }

    public void followUser(){
        // check if the field isn't empty
        String twitterUser = userId.getText();
        if(twitterUser.trim().equals("")){
            JOptionPane.showMessageDialog(frame, "Error: Enter a user ID !");
            userId.setText("");
            return;
        }

        // can't follow yourself !!
        if(twitterUser.equals(user.getUserId())){
            JOptionPane.showMessageDialog(frame, "Error: Can't follow yourself !");
            userId.setText("");
            return;
        }

        // check if user is already being followed
        if(user.checkAlreadyFollowing(twitterUser)){
            JOptionPane.showMessageDialog(frame, "Error: Already following that user !");
            userId.setText("");
            return;
        }

        // otherwise check if that user exists then...
        if(adminPanel.userController.checkUserExists(twitterUser)){
            User followingUser = adminPanel.userController.grabUser(twitterUser);
            // add them to your following list
            // add yourself to their followers list
            // register to observer to get tweets (Observer Pattern)
            user.addToFollowing(followingUser);
            followingUser.addToFollowers(user);
            followingUser.register(user);
            System.out.println(user.getUserId() + " is now following " +twitterUser );
        }else{
            JOptionPane.showMessageDialog(frame, "Error: User does not exist");
        }

        userId.setText("");
    }

    public static void main(String[] args){
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserUI().setVisible(true);
            }
        });
    }
}
