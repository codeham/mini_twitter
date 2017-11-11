import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminPanel extends JFrame implements UIBuild{
    // main frame
    JFrame frame;

    // user controller
    UserController userController;

    // treeview
    JScrollPane treeview;
    private JTree tree;

    // buttons
    private JButton adduser;
    private JButton addGroup;
    private JButton userView;
    private JButton userTotal;
    private JButton messageTotal;
    private JButton groupTotal;
    private JButton positivePercentage;

    // text fields
    private JTextField userId;
    private JTextField groupId;

    // labels
    private JLabel userIdLabel;
    private JLabel groupIdLabel;

    private JButton[] buttons;

    // static instance
    private static AdminPanel instance = new AdminPanel();

    public AdminPanel(){
        UIbuilder();
    }

    public static AdminPanel getInstance(){
        return instance;
    }

    public void UIbuilder(){
        userController = new UserController();
        treeManager();
        buttonManager();
        labelManager();
        textManager();
        frameManager();

    }

    public void testLayouts(){

    }

    public void frameManager(){
        // initializer frame
        frame = new JFrame();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Mini Twitter");
        frame.setResizable(false);
        frame.setLayout(null);

        frame.add(treeview);
        frame.add(adduser);
        frame.add(addGroup);

        frame.add(userId);
        frame.add(groupId);

        frame.add(userIdLabel);
        frame.add(groupIdLabel);

        frame.add(userView);

        frame.add(userTotal);
        frame.add(messageTotal);

        frame.add(groupTotal);
        frame.add(positivePercentage);

        // initialize panel
//        JPanel panel = new JPanel();

//        frame.add(panel);
//        TextArea x = new TextArea(5,30);
//        JTextField field = new JTextField(20);
//        frame.setLayout(new FlowLayout());
//        panel.add(x);
//
//        JButton button = new JButton("Click here to get scammed");
//        panel.add(button);



        frame.setSize(715,330);
        frame.setVisible(true);

    }

    public void treeManager(){
        // initialize tree nodes
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");

        tree = new JTree(root);
        treeview = new JScrollPane(tree);
        treeview.setBounds(5,5,245,275);
    }

    public void textManager(){
        // user ID label
        userId = new JTextField();
        userId.setBounds(395, 5, 150, 35);

        // group ID label
        groupId = new JTextField();
        groupId.setBounds(395, 41, 150, 35);
    }

    public void labelManager(){
        userIdLabel = new JLabel();
        userIdLabel.setText("User ID");
        userIdLabel.setBounds(340, 5, 150, 35);

        groupIdLabel = new JLabel();
        groupIdLabel.setText("Group ID");
        groupIdLabel.setBounds(337, 41, 150, 35);
    }

    public void addGroup(){
        TreePath[] tp = tree.getSelectionPaths();
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        DefaultMutableTreeNode newGroup = new DefaultMutableTreeNode(groupId.getText());
        DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) tree.getModel().getRoot();
        boolean setText = false;

        if(tp != null){
            // selection paths are not empty
            for(TreePath tree: tp){
                if(!groupId.getText().trim().equals("") && ((DefaultMutableTreeNode)tree.getLastPathComponent()).getAllowsChildren()){
                    // text field is not empty and the specified path in the tree allows children leafs
//                  add folder to tree, come back to this
                    rootNode.add(new DefaultMutableTreeNode(groupId.getText()));
                    userController.addGroup(groupId.getText());
                    model.reload();
                    groupId.setText("");
                    setText = true;
                }
            }
        }else if (tp == null && !groupId.getText().trim().equals("")){
            // user has not selected path
            // insert user into root by default
            model.insertNodeInto(newGroup, rootNode, rootNode.getChildCount());
            model.reload();
            groupId.setText("");
            setText = true;
        }

        if(groupId.getText().trim().equals("") && !setText){
            // completely empty input field
            JOptionPane.showMessageDialog(frame, "Error: No user group to add !");
        }

    }

    public void addUser(){
        TreePath[] tp = tree.getSelectionPaths();
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        DefaultMutableTreeNode newUser = new DefaultMutableTreeNode(userId.getText());
        DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) tree.getModel().getRoot();
        boolean setText = false;

        if(tp != null){
            // selection paths are not empty
            for(TreePath tree: tp){
                if(!userId.getText().trim().equals("") && ((DefaultMutableTreeNode)tree.getLastPathComponent()).getAllowsChildren()){
                    // text field is not empty and the specified path in the tree allows children leafs
                    model.insertNodeInto(newUser, selectedNode, selectedNode.getChildCount());
                    userController.addUser(userId.getText());
                    model.reload();
                    userId.setText("");
                    setText = true;
                }
            }
        }else if (tp == null && !userId.getText().trim().equals("")){
            // user has not selected path
            // insert user into root by default
            model.insertNodeInto(newUser, rootNode, rootNode.getChildCount());
            userController.addUser(userId.getText());
            model.reload();
            userId.setText("");
            setText = true;
        }

        if(userId.getText().trim().equals("") && !setText){
            // completely empty input field case
            JOptionPane.showMessageDialog(frame, "Error: No user to add !");
        }

    }

    public void buttonManager(){
        // add user button
        adduser = new JButton();
        adduser.setText("Add User");
        adduser.setBounds(550, 5, 150, 35);
        adduser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUser();
            }
        });

        // add group button
        addGroup = new JButton();
        addGroup.setText("Add Group");
        addGroup.setBounds(550, 41, 150, 35);
        addGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addGroup();
            }
        });

        // open user view button
        userView = new JButton();
        userView.setText("Open User View");
        userView.setBounds(395, 85, 305, 35);

        // user total button
        userTotal = new JButton();
        userTotal.setText("User Total");
        userTotal.setBounds(390, 200, 150, 35);

        // message total button
        messageTotal = new JButton();
        messageTotal.setText("Message Total");
        messageTotal.setBounds(390, 240, 150, 35);

        // show group total
        groupTotal = new JButton();
        groupTotal.setText("Group Total");
        groupTotal.setBounds(550, 200, 150, 35);

        // show positive percentage
        positivePercentage = new JButton();
        positivePercentage.setText("Positive Percentage");
        positivePercentage.setBounds(550, 240, 150, 35);
    }
}
