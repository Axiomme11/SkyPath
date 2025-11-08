import jdk.dynalink.Operation;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class MainSwing {

    private static final int SIDEBAR_EXPANDED_WIDTH = 220;
    private static final int SIDEBAR_COLLAPSED_WIDTH = 60;
    public static SwingUtilities SwingUtilities;

    private JFrame frame;
    private JPanel sidebar;
    private JPanel contentArea;
    private boolean sidebarExpanded = true;

    public MainSwing() {
        initUI();
    }

    private void initUI() {
        frame = new JFrame("SkyPath v1.0");

        try {
            Image icon = ImageIO.read(Objects.requireNonNull(getClass().getResource("/ressources/icon2d.png")));
            assert frame != null;
            frame.setIconImage( icon );

        } catch (Exception e) {
            e.printStackTrace();
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 650);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        sidebar = createSidebar();
        contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(new Color(0xF7F9FC));

        frame.add(sidebar, BorderLayout.WEST);
        frame.add(contentArea, BorderLayout.CENTER);

        showMenu1View(); // affichage par défaut
        frame.setVisible(true);
    }

    private JPanel createSidebar() {
        JPanel box = new JPanel();
        box.setPreferredSize(new Dimension(SIDEBAR_EXPANDED_WIDTH, 0));
        box.setLayout(new BorderLayout());
        box.setBackground(new Color(0x1E5AA8));
        box.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Top area with icon and title
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
        top.setOpaque(false);
        JButton toggleBtn = new JButton("\u2630"); // simple "menu" icon
        toggleBtn.setFocusable(false);
        toggleBtn.setBackground(new Color(0xBBD7FF));
        toggleBtn.setBorder(BorderFactory.createLineBorder(new Color(0x8FB9FF)));
        toggleBtn.addActionListener(e -> toggleSidebar());
        JLabel title = new JLabel("NavTools");
        title.setForeground(Color.WHITE);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 16f));
        top.add(toggleBtn);
        top.add(title);
        box.add(top, BorderLayout.NORTH);

        // Center: menu buttons
        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBorder(new EmptyBorder(20, 0, 0, 0));

        JButton menu1 = new JButton("Calculs V/D/T");
        styleSidebarButton(menu1);
        JButton menu2 = new JButton("Pythagore");
        styleSidebarButton(menu2);

        menu1.addActionListener(e -> showMenu1View());
        menu2.addActionListener(e -> showMenu2ViewEmpty());

        center.add(menu1);
        center.add(Box.createVerticalStrut(8));
        center.add(menu2);

        box.add(center, BorderLayout.CENTER);

        // Footer
        JLabel footer = new JLabel("v1.0");
        footer.setForeground(new Color(0xD9F0FF));
        JPanel foot = new JPanel(new FlowLayout(FlowLayout.LEFT));
        foot.setOpaque(false);
        foot.add(footer);
        box.add(foot, BorderLayout.SOUTH);

        return box;
    }

    private void styleSidebarButton(JButton b) {
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        b.setAlignmentX(Component.LEFT_ALIGNMENT);
        b.setBackground(new Color(0,0,0,0));
        b.setForeground(Color.WHITE);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setOpaque(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                b.setOpaque(true);
                b.setBackground(new Color(0x2A79C4));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                b.setOpaque(false);
                b.setBackground(new Color(0,0,0,0));
            }
        });
    }

    private void toggleSidebar() {
        sidebarExpanded = !sidebarExpanded;
        int width = sidebarExpanded ? SIDEBAR_EXPANDED_WIDTH : SIDEBAR_COLLAPSED_WIDTH;
        sidebar.setPreferredSize(new Dimension(width, 0));
        // optionally hide labels inside when collapsed: iterate and hide text for buttons
        for (Component c : ((JPanel) sidebar.getComponent(1)).getComponents()) {
            if (c instanceof JButton) {
                JButton btn = (JButton) c;
                if (!sidebarExpanded) {
                    btn.setText("");
                } else {
                    // restore text based on action command (simple heuristic)
                    if (btn == ((JPanel) sidebar.getComponent(1)).getComponent(0)) btn.setText("Calculs V/D/T");
                    if (btn == ((JPanel) sidebar.getComponent(1)).getComponent(2)) btn.setText("Pythagore");
                }
            }
        }
        sidebar.revalidate();
        frame.repaint();
    }

    // Menu 1: split right area into three panels (Temps, Distance, Vitesse)
    private void showMenu1View() {
        contentArea.removeAll();
        JPanel panel = new JPanel(new GridLayout(1, 3, 16, 0));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(0xF7F9FC));

        panel.add( createControlPanel( "Temps (h)", "Distance (NM)", "Vitesse (kts)", "Calculer Temps", "Main.CalcTemps()" ));
        panel.add( createControlPanel( "Distance (NM)", "Vitesse (kts)", "Temps (h)", "Calculer Distance", "Main.CalcDistance()" ));
        panel.add( createControlPanel( "Vitesse (kts)", "Distance (NM)", "Temps (h)", "Calculer Vitesse", "Main.CalcVitesse()" ));

        contentArea.add(panel, BorderLayout.CENTER);
        contentArea.revalidate();
        contentArea.repaint();
    }

    // Menu 2: Pythagore empty view
    private void showMenu2ViewEmpty() {
        contentArea.removeAll();
        JPanel v = new JPanel();
        v.setLayout(new BoxLayout(v, BoxLayout.Y_AXIS));
        v.setBackground(new Color(0xF7F9FC));
        v.setBorder(new EmptyBorder(30, 30, 30, 30));
        JLabel title = new JLabel("Pythagore");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        title.setForeground(new Color(0x24476B));
        JLabel note = new JLabel("Section Pythagore (aucun contenu ajouté)");
        note.setForeground(new Color(0x677D8C));
        note.setBorder(new EmptyBorder(12, 0, 0, 0));
        v.add(title);
        v.add(note);
        contentArea.add(v, BorderLayout.NORTH);
        contentArea.revalidate();
        contentArea.repaint();
    }

    // Helper to create a control panel column (UI only)
    private JPanel createControlPanel(String titleText, String field1Label, String field2Label, String buttonText, String fonctionBtn) {
        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xD6E6FB)),
                new EmptyBorder(12, 12, 12, 12)
        ));
        box.setBackground(Color.WHITE);

        JLabel title = new JLabel(titleText);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 14f));
        title.setForeground(new Color(0x1B3C5B));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel l1 = new JLabel(field1Label);
        l1.setAlignmentX(Component.LEFT_ALIGNMENT);
        JTextField tf1 = new JTextField();
        tf1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        tf1.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel l2 = new JLabel(field2Label);
        l2.setAlignmentX(Component.LEFT_ALIGNMENT);
        JTextField tf2 = new JTextField();
        tf2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        tf2.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton calc = new JButton(buttonText);
        calc.setAlignmentX(Component.LEFT_ALIGNMENT);
        calc.setBackground(new Color(0x1E88E5));
        calc.setForeground(Color.WHITE);
        calc.setFocusPainted(false);
        calc.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel input1 = new JLabel(" ");
        input1.setForeground(new Color(0x0B5A83));
        input1.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel input2 = new JLabel(" ");
        input2.setForeground(new Color(0x0B5A83));
        input2.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel resultLbl = new JLabel(" ");
        input2.setForeground(new Color(0x0B5A83));
        input2.setAlignmentX(Component.LEFT_ALIGNMENT);

        calc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String a = tf1.getText().isEmpty() ? "?" : tf1.getText();
                String b = tf2.getText().isEmpty() ? "?" : tf2.getText();

                input1.setText("Entrées : " + field1Label + " = " + a);
                input2.setText("Entrées : " + field2Label + " = " + b);
                System.out.println("Entrées : " + field1Label + " = " + a + " ; " + field2Label + " = " + b);

                if ( Objects.equals(a, "?") || Objects.equals(b, "?") ) {
                    resultLbl.setText( "Veuillez saisir des chiffres uniquement !" );
                    System.out.println("Veuillez saisir des chiffres uniquement !" );

                } else if ( Objects.equals(fonctionBtn, "Main.CalcTemps()") ) {
                    double result = 0.0;
                    result = Main.CalcTemps( Double.parseDouble(a), Double.parseDouble(b) );
                    resultLbl.setText( "Sortie : " + field1Label + " = " + result );
                    System.out.println( "Sortie : " + field1Label + " = " + result );


                }
            }
        });

        box.add(title);
        box.add(Box.createVerticalStrut(8));
        box.add(l1);
        box.add(tf1);
        box.add(Box.createVerticalStrut(8));
        box.add(l2);
        box.add(tf2);
        box.add(Box.createVerticalStrut(12));
        box.add(calc);
        box.add(Box.createVerticalStrut(12));
        box.add(input1);
        box.add(input2);
        box.add(resultLbl);
        return box;
    }
    
}