package TravelBudgetPlanner;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

//============================================================
//MAIN GUI CLASS
//============================================================
public class TravelPlannerGUI extends JFrame implements ActionListener {

private static final String[] CITIES = {"Mumbai","Goa","Delhi","Pune","Jaipur","Manali","Kerala"};

// Colours
private static final Color BG    = new Color(15, 20, 45);
private static final Color CARD  = new Color(25, 32, 65);
private static final Color GOLD  = new Color(212, 175, 55);
private static final Color CYAN  = new Color(0, 210, 190);
private static final Color WHITE = new Color(230, 235, 255);
private static final Color INPBG = new Color(35, 44, 80);
private static final Color GREEN = new Color(50, 220, 130);
private static final Color RED   = new Color(255, 85, 85);

private JComboBox<String> srcBox, destBox, planBox, transportBox;
private JTextField daysField, peopleField, budgetField;
private JTextArea  resultArea;
private JLabel     statusLabel;

public TravelPlannerGUI() {
    setTitle("Smart Travel Budget Planner");
    setSize(620, 700);
    setMinimumSize(new Dimension(580, 660));
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());
    getContentPane().setBackground(BG);

    // HEADER
    JPanel header = new JPanel(new GridLayout(2,1));
    header.setBackground(CARD);
    header.setBorder(BorderFactory.createEmptyBorder(14,24,12,24));
    JLabel title = lbl("  SMART TRAVEL BUDGET PLANNER", GOLD, new Font("Georgia", Font.BOLD, 20));
    JLabel sub   = lbl("  Select cities, choose your plan and press Calculate", new Color(160,165,200), new Font("SansSerif", Font.PLAIN, 12));
    header.add(title); header.add(sub);
    add(header, BorderLayout.NORTH);

    // FORM
    JPanel form = new JPanel(new GridLayout(8, 2, 10, 10));
    form.setBackground(BG);
    form.setBorder(BorderFactory.createEmptyBorder(22,32,10,32));

    srcBox       = combo(CITIES);
    destBox      = combo(CITIES); destBox.setSelectedIndex(1);
    daysField    = field();
    peopleField  = field();
    budgetField  = field();
    planBox      = combo(new String[]{"Budget  (Economy Stay)","Standard  (Comfortable)","Luxury  (Premium)"});
    transportBox = combo(new String[]{"Bus","Train","Flight"});

    row(form, "Source City",       srcBox);
    row(form, "Destination City",  destBox);
    row(form, "Number of Days",    daysField);
    row(form, "Number of People",  peopleField);
    row(form, "Your Budget (Rs.)", budgetField);
    row(form, "Plan Type",         planBox);
    row(form, "Transport Mode(Return Journey)",    transportBox);

    JButton btn = new JButton("  CALCULATE TRIP COST  ");
    btn.setFont(new Font("Georgia", Font.BOLD, 14));
    btn.setBackground(GOLD); btn.setForeground(new Color(255,255,0));
    btn.setFocusPainted(false); btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    btn.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
    btn.addActionListener(this);
    btn.addMouseListener(new MouseAdapter() {
        public void mouseEntered(MouseEvent e) { btn.setBackground(CYAN); }
        public void mouseExited (MouseEvent e) { btn.setBackground(GOLD); }
    });
    form.add(new JLabel());
    form.add(btn);
    add(form, BorderLayout.CENTER);

    // RESULT
    JPanel resWrap = new JPanel(new BorderLayout());
    resWrap.setBackground(BG);
    resWrap.setBorder(BorderFactory.createEmptyBorder(6,32,20,32));

    JPanel topBar = new JPanel(new BorderLayout());
    topBar.setBackground(CARD);
    JLabel heading = lbl("  TRIP SUMMARY", GOLD, new Font("Georgia", Font.BOLD, 13));
    heading.setBorder(BorderFactory.createEmptyBorder(6,10,6,10));
    statusLabel = lbl("  Awaiting input...", new Color(160,165,200), new Font("SansSerif", Font.BOLD, 12));
    statusLabel.setBorder(BorderFactory.createEmptyBorder(6,10,6,10));
    topBar.add(heading, BorderLayout.WEST);
    topBar.add(statusLabel, BorderLayout.EAST);

    resultArea = new JTextArea(9, 40);
    resultArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
    resultArea.setBackground(new Color(8,10,22));
    resultArea.setForeground(CYAN);
    resultArea.setEditable(false);
    resultArea.setBorder(BorderFactory.createEmptyBorder(10,14,10,14));
    resultArea.setText("\n   Fill in the form above and press CALCULATE.\n");

    JScrollPane scroll = new JScrollPane(resultArea);
    scroll.setBorder(BorderFactory.createLineBorder(GOLD, 1));
    resWrap.add(topBar, BorderLayout.NORTH);
    resWrap.add(scroll, BorderLayout.CENTER);
    add(resWrap, BorderLayout.SOUTH);

    setVisible(true);
}

// --- Helpers ---
private JComboBox<String> combo(String[] items) {
    JComboBox<String> c = new JComboBox<>(items);
    c.setFont(new Font("SansSerif", Font.PLAIN, 13));
    c.setBackground(INPBG); c.setForeground(Color.BLACK);
    return c;
}
private JTextField field() {
    JTextField t = new JTextField();
    t.setFont(new Font("Monospaced", Font.PLAIN, 13));
    t.setBackground(INPBG); t.setForeground(WHITE); t.setCaretColor(GOLD);
    t.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(60,70,110),1),
        BorderFactory.createEmptyBorder(5,8,5,8)));
    return t;
}
private void row(JPanel p, String text, JComponent comp) {
    JLabel l = lbl("  " + text, WHITE, new Font("SansSerif", Font.BOLD, 13));
    p.add(l); p.add(comp);
}
private JLabel lbl(String t, Color c, Font f) {
    JLabel l = new JLabel(t); l.setForeground(c); l.setFont(f); return l;
}

// --- CALCULATE ACTION ---
public void actionPerformed(ActionEvent e) {
    try {
        String src  = (String) srcBox.getSelectedItem();
        String dest = (String) destBox.getSelectedItem();
        if (src.equals(dest)) throw new InvalidTripInputException("Source and Destination must be different cities.");

        int days, people; double budget;
        try {
            days   = Integer.parseInt(daysField.getText().trim());
            people = Integer.parseInt(peopleField.getText().trim());
            budget = Double.parseDouble(budgetField.getText().trim());
        } catch (NumberFormatException ex) {
            throw new InvalidTripInputException("Days, People and Budget must be valid numbers.");
        }
        if (days<=0||people<=0||budget<=0)
            throw new InvalidTripInputException("Days, People and Budget must all be greater than 0.");

        String planStr = (String) planBox.getSelectedItem();
        TravelPlan plan = planStr.startsWith("Budget") ? new BudgetPlan()
                        : planStr.startsWith("Standard") ? new StandardPlan() : new LuxuryPlan();

        double hotel  = plan.getHotelTotal(days, people);
        double food   = plan.getFoodTotal(days, people);
        String mode   = (String) transportBox.getSelectedItem();
        int    dist   = TravelCostCalculator.getDistance(src, dest);
        double travel = TravelCostCalculator.getTransportCost(dist, mode, people);
        double total  = hotel + food + travel;

        String line = "-".repeat(40);
        String out  = "\n  Route       : " + src + " -> " + dest
                    + "\n  Distance    : " + dist + " km  |  Transport: " + mode
                    + "\n  Days: " + days + "   |   People: " + people
                    + "   |   Plan: " + planStr.split("  ")[0]
                    + "\n" + line
                    + "\n  Hotel Cost  : Rs. " + String.format("%,10.0f", hotel)
                    + "\n  Food Cost   : Rs. " + String.format("%,10.0f", food)
                    + "\n  Travel Cost : Rs. " + String.format("%,10.0f", travel)
                    + "\n" + line
                    + "\n  TOTAL COST  : Rs. " + String.format("%,10.0f", total)
                    + "\n  YOUR BUDGET : Rs. " + String.format("%,10.0f", budget)
                    + "\n" + line + "\n";

        if (total > budget) {
            out += "  WARNING: BUDGET EXCEEDED by Rs. " + String.format("%,.0f", total-budget) + "!\n"
                 + "  Tip: Try Budget plan, Bus/Train, or reduce days.\n";
            resultArea.setForeground(RED);
            statusLabel.setForeground(RED); statusLabel.setText("  Budget Exceeded  ");
        } else {
            out += "  YES! YOU CAN TRAVEL :)   Rs. " + String.format("%,.0f", budget-total) + " to spare!\n";
            resultArea.setForeground(GREEN);
            statusLabel.setForeground(GREEN); statusLabel.setText("  Within Budget  ");
        }
        resultArea.setText(out);
        FileManager.saveTrip(src+"->"+dest+"|People:"+people+"|Days:"+days+"|Rs."+String.format("%.0f",total));

    } catch (InvalidTripInputException ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage(), "Invalid Input", JOptionPane.WARNING_MESSAGE);
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Unexpected error: "+ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

public static void main(String[] args) {
    SwingUtilities.invokeLater(TravelPlannerGUI::new);
}
}

