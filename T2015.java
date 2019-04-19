import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComponent;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Graphics;

// Data Structures 2015 Coursework
// Student: Valtteri Ylisalo
// Student Number: 415552
// E-mail: Ylisalo.T.Valtteri@student.uta.fi
// Tasks programmed: Graph (1p)
// Graph implementation: Edge list

public class T2015 implements ActionListener { //Our 2015 implements ActionListener interface.
    private JFrame f; //Main window.
    private JPanel p; //A drawing area inside main window.
    private JButton btn1;
    private JButton btn2;
    private JButton btn3;
    private JButton btn4;
    private JButton btn5;
    private JLabel lbl;
    private TGraph tg;

    public void setDataStructure() {
        tg = new TGraph();
	}

    public void renderPoints() {
        Graphics g = p.getGraphics(); //Graphics context of JPanel.
        int [][] drawdata = tg.getGraphData();
        try {
            for (int i = 0; i < 99; i++) {
                g.setColor(Color.RED);
                g.drawRect(drawdata[i][0],drawdata[i][1],5,5);
                g.setColor(Color.BLUE);
                g.drawLine(drawdata[i][0],drawdata[i][1],drawdata[drawdata[i][2]-1][0],drawdata[drawdata[i][2]-1][1]);
                g.drawLine(drawdata[i][0],drawdata[i][1],drawdata[drawdata[i][3]-1][0],drawdata[drawdata[i][3]-1][1]);
                g.drawLine(drawdata[i][0],drawdata[i][1],drawdata[drawdata[i][4]-1][0],drawdata[drawdata[i][4]-1][1]);
                Thread.sleep(200);
            }
        }
        catch (Exception e) {
            System.out.println("Error");
        }
    }
	
    // Clears (basically empties) the screen from previous graph rendering.
    public void clearScreen() {
        Graphics g = p.getGraphics();
        g.setColor(Color.GREEN);
        g.fill3DRect(10,10,800,600,true);
    }

    public void actionPerformed(ActionEvent e) { //This function is called when a button is pressed.
        String source = e.getActionCommand();
        if (source.equals("Graph")) {
            lbl.setText(source);
            // Prepares the graph by replacing the graphdata content with data of vertices and edges.
            tg.prepareGraph();
            // Clear screen from previous graph rendering.
            clearScreen();
            // Render the graph on the screen.
            renderPoints();
        }
		
        if (source.equals("Traverse1")) {
            lbl.setText(source);
            // Clear screen from previous graph rendering.
            clearScreen();
        }

        if (source.equals("Traverse2")) {
            lbl.setText(source);
            // Clear screen from previous graph rendering.
            clearScreen();
        }

        if (source.equals("Minimum")) {
            lbl.setText(source);
            // Clear screen from previous graph rendering.
            clearScreen();
        }

        if (source.equals("Floyd")) {
            lbl.setText(source);
            // Clear screen from previous graph rendering.
            clearScreen();
        }
    }

    public void buildGUI() {
        f = new JFrame();
        f.setLayout(null); //We do not use any predefined layout.
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setBounds(50,50,1024,768); //Extents for main window.
        p = new JPanel();
        p.setBounds(10,10,800,600); //Extents for drawing area.
        p.setBackground(Color.GREEN);
        btn1 = new JButton("Graph");
        btn1.setBounds(850,10,100,20);	//Extents for a button.
        btn1.addActionListener(this);   //An action listener is connected to a button.
        btn2 = new JButton("Traverse1");
        btn2.setBounds(850,50,100,20);
        btn2.addActionListener(this);
        btn3 = new JButton("Traverse2");
        btn3.setBounds(850,90,100,20);
        btn3.addActionListener(this);	
        btn4 = new JButton("Minimum");
        btn4.setBounds(850,130,100,20);
        btn4.addActionListener(this);	
        btn5 = new JButton("Floyd");
        btn5.setBounds(850,170,100,20);
        btn5.addActionListener(this);	
        lbl = new JLabel("Tira2015");
        lbl.setBounds(850,210,100,20);
        f.add(p);  //Add drawing area inside the main window.
        f.add(btn1);
        f.add(btn2);
        f.add(btn3);
        f.add(btn4);
        f.add(btn5);
        f.add(lbl);
    }

    public static void main(String[] args) {
        T2015 ht = new T2015();		      
        ht.buildGUI();
        ht.setDataStructure();
    }
}