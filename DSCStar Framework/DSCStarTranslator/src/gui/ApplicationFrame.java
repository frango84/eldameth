/*****************************************************************
DSC* TRANSLATOR
Copyright (C) 2010 G. Fortino, F. Rango

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; 
version 2.1 of the License.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
*****************************************************************/

package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.TitledBorder;

import org.w3c.dom.Document;

import com.mxgraph.io.mxCodec;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxUndoManager;
import com.mxgraph.util.mxUndoableEdit;
import com.mxgraph.util.mxUtils;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.util.mxUndoableEdit.mxUndoableChange;
import com.mxgraph.view.mxGraph;

import logic.Translator;

/**
 * Application frame.
 * 
 * @author G. Fortino, F. Rango
 */
public class ApplicationFrame extends JFrame implements Observer {
	
	private JTextField textFieldSourceFile;
	private JTextField textFieldDestFile;
	private File sourceFile;
	private File destFile;
	private Translator translator;
	private JPanel panelDSCStar;
	private JMenuBar menuBar;
	private JMenu menuFile;
	private JMenuItem miOpenDSCStar;
	private JMenuItem miSaveDSCStar;
	private JMenuItem miSelectSourceFile;
	private JMenuItem miSelectDestFile;
	private JMenu submenuTranslationType;
	private JRadioButtonMenuItem radioKeyStatements;
	private JRadioButtonMenuItem radioFull;
	private JMenuItem miStartTranslation;
	private JMenu menuEdit;
	private JMenuItem miUndo;
	private JMenuItem miRedo;
	private JMenu menuInfo;
	private JMenuItem miAbout;
	private JFrame progressFrame;
	private mxGraphComponent graphComponent;
	mxUndoManager undoManager;

	public ApplicationFrame() {
		sourceFile = null;
		destFile = null;
		graphComponent = null;
		undoManager = null;

		//impostiamo il pattern observer
		this.translator = new Translator();
		this.translator.addObserver(this);

		setSize(700, 700); //ATTENZIONE: non cambiare la larghezza (altri componenti dipendono da questa dimensione)
		setTitle("DSC* TRANSLATOR");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		getContentPane().setVisible(true);
		
		JPanel panelPrincipale = new JPanel();
		panelPrincipale.setLayout(new BoxLayout(panelPrincipale, BoxLayout.Y_AXIS));
		getContentPane().add(panelPrincipale);
		panelPrincipale.setVisible(true);
		panelPrincipale.add(Box.createRigidArea(new Dimension(0, 20)));
		
		JPanel panelSourceFile = new JPanel();
		panelSourceFile.setLayout(new BoxLayout(panelSourceFile, BoxLayout.X_AXIS));
		panelSourceFile.add(Box.createRigidArea(new Dimension(20, 0)));
		JLabel labelSourceFile = new JLabel("SELECTED SOURCE FILE:");
		labelSourceFile.setFont(new Font("Arial", Font.BOLD, 14));
		labelSourceFile.setForeground(Color.BLACK);
		panelSourceFile.add(labelSourceFile);
		panelSourceFile.add(Box.createRigidArea(new Dimension(20, 0)));
		textFieldSourceFile = new JTextField();
		textFieldSourceFile.setText("");
		textFieldSourceFile.setForeground(Color.BLUE);
		textFieldSourceFile.setFont(new Font("Arial", Font.BOLD, 14));
		textFieldSourceFile.setEditable(false);
		panelSourceFile.add(textFieldSourceFile);
		panelSourceFile.add(Box.createRigidArea(new Dimension(20, 0)));
		panelSourceFile.setSize(700, 25);
		panelSourceFile.setPreferredSize(new Dimension(700, 25));
		panelSourceFile.setMinimumSize(new Dimension(700, 25));
		panelSourceFile.setMaximumSize(new Dimension(700, 25));
		
		panelPrincipale.add(panelSourceFile);
		panelPrincipale.add(Box.createRigidArea(new Dimension(0, 20)));

		JPanel panelDestFile = new JPanel();
		panelDestFile.setLayout(new BoxLayout(panelDestFile, BoxLayout.X_AXIS));
		panelDestFile.add(Box.createRigidArea(new Dimension(20, 0)));
		JLabel labelDestFile = new JLabel("SELECTED DESTINATION FILE:");
		labelDestFile.setFont(new Font("Arial", Font.BOLD, 14));
		labelDestFile.setForeground(Color.BLACK);
		panelDestFile.add(labelDestFile);
		panelDestFile.add(Box.createRigidArea(new Dimension(20, 0)));
		textFieldDestFile = new JTextField();
		textFieldDestFile.setText("");
		textFieldDestFile.setForeground(Color.BLUE);
		textFieldDestFile.setFont(new Font("Arial", Font.BOLD, 14));
		textFieldDestFile.setEditable(false);
		panelDestFile.add(textFieldDestFile);
		panelDestFile.add(Box.createRigidArea(new Dimension(20, 0)));
		panelDestFile.setSize(700, 25);
		panelDestFile.setPreferredSize(new Dimension(700, 25));
		panelDestFile.setMinimumSize(new Dimension(700, 25));
		panelDestFile.setMaximumSize(new Dimension(700, 25));

		panelPrincipale.add(panelDestFile);
		panelPrincipale.add(Box.createRigidArea(new Dimension(0, 20)));

		JPanel panelContainingPanelDSCStar = new JPanel();
		panelContainingPanelDSCStar.setLayout(new BoxLayout(panelContainingPanelDSCStar, BoxLayout.X_AXIS));
		panelContainingPanelDSCStar.add(Box.createRigidArea(new Dimension(20, 0)));
		panelDSCStar = new JPanel();
		TitledBorder titledBorder = BorderFactory.createTitledBorder("Resulting graphic DSC*");
		titledBorder.setTitleColor(Color.BLACK);
		panelDSCStar.setBorder(titledBorder);
		panelDSCStar.setLayout(new BorderLayout());
		panelContainingPanelDSCStar.add(panelDSCStar);
		panelContainingPanelDSCStar.add(Box.createRigidArea(new Dimension(20, 0)));
		
		panelPrincipale.add(panelContainingPanelDSCStar);
		panelPrincipale.add(Box.createRigidArea(new Dimension(0, 20)));

		//menu
		menuBar = new JMenuBar();
		
		menuFile = new JMenu("File");
		miOpenDSCStar = new JMenuItem("Open graphic DSC*");
		menuFile.add(miOpenDSCStar);
		miSaveDSCStar = new JMenuItem("Save graphic DSC*");
		menuFile.add(miSaveDSCStar);
		miSelectSourceFile = new JMenuItem("Select source file");
		menuFile.add(miSelectSourceFile);
		miSelectDestFile = new JMenuItem("Select destination file");
		menuFile.add(miSelectDestFile);
		
		submenuTranslationType = new JMenu("Select translation type");
		ButtonGroup group = new ButtonGroup();
		radioFull = new JRadioButtonMenuItem("FULL");
		group.add(radioFull);
		radioFull.setSelected(true);
		submenuTranslationType.add(radioFull);
		radioKeyStatements = new JRadioButtonMenuItem("KEY STATEMENTS BASED");
		group.add(radioKeyStatements);
		submenuTranslationType.add(radioKeyStatements);
		menuFile.add(submenuTranslationType);
		
		miStartTranslation = new JMenuItem("Start translation");
		menuFile.add(miStartTranslation);
		menuBar.add(menuFile);
		
		menuEdit = new JMenu("Edit");
		miUndo = new JMenuItem("Undo");
		menuEdit.add(miUndo);
		miRedo = new JMenuItem("Redo");
		menuEdit.add(miRedo);
		menuBar.add(menuEdit);
		
		menuInfo = new JMenu("Info");
		miAbout = new JMenuItem("About");
		menuInfo.add(miAbout);
		menuBar.add(menuInfo);
		
		setJMenuBar(menuBar);
		
		//disabilitiamo gli item del menu relativi al DSC* grafico
		miSaveDSCStar.setEnabled(false);
		miUndo.setEnabled(false);
		miRedo.setEnabled(false);
		
		//listner
		MenuListener listener = new MenuListener();
		miOpenDSCStar.addActionListener(listener);
		miSaveDSCStar.addActionListener(listener);
		miSelectSourceFile.addActionListener(listener);
		miSelectDestFile.addActionListener(listener);
		miStartTranslation.addActionListener(listener);
		miAbout.addActionListener(listener);
		miUndo.addActionListener(listener);
		miRedo.addActionListener(listener);

		setLocationOfFrame(this);
	}
	
	private void setLocationOfFrame(Component frame){
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame.getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		frame.setLocation((screenSize.width - frameSize.width) / 2,
				(screenSize.height - frameSize.height) / 2);
	}
	
	private void initGraphComponent(mxGraph graph){
		//questo metodo inizializza il DSC* grafico con quello passato tramite parametro
		
		//aggiorniamo il DSC* grafico
		panelDSCStar.removeAll();
		this.graphComponent = new mxGraphComponent(graph);
		this.graphComponent.setConnectable(false); //disabilita la creazione di nuove transizioni
		this.graphComponent.setFoldingEnabled(false); //disabilita il folding degli stati composti
		this.graphComponent.getViewport().setOpaque(false);
		this.graphComponent.setBackground(Color.WHITE); //setta lo sfondo bianco
		panelDSCStar.add(this.graphComponent, BorderLayout.CENTER);
		this.validate();

		//inizializziamo i listener per l'undo-redo sul nuovo DSC* grafico
		undoManager = new mxUndoManager();
		mxIEventListener undoHandler = new mxIEventListener(){
			public void invoke(Object source, mxEventObject evt){
				undoManager.undoableEditHappened((mxUndoableEdit) evt.getProperty("edit"));
			}
		};
		this.graphComponent.getGraph().getModel().addListener(mxEvent.UNDO, undoHandler);
		this.graphComponent.getGraph().getView().addListener(mxEvent.UNDO, undoHandler);
		mxIEventListener changeHandler = new mxIEventListener(){
			public void invoke(Object source, mxEventObject evt){
				List<mxUndoableChange> changes = ((mxUndoableEdit) evt.getProperty("edit")).getChanges();
				graphComponent.getGraph().setSelectionCells(graphComponent.getGraph()
						.getSelectionCellsForChanges(changes));
			}
		};
		undoManager.addListener(mxEvent.UNDO, changeHandler);
		undoManager.addListener(mxEvent.REDO, changeHandler);
		
		//abilitiamo gli item del menu relativi al DSC* grafico
		miSaveDSCStar.setEnabled(true);
		miUndo.setEnabled(true);
		miRedo.setEnabled(true);
	}
	
	public void update(Observable o, Object arg) {
		Integer i = (Integer) arg;
		if(i.intValue() == Translator.END){
			//chiudiamo il frame con la barra grafica di avanzamento e abilitiamo il frame corrente
			this.progressFrame.dispose();
			this.setEnabled(true);
			
			//aggiorniamo il DSC* grafico
			initGraphComponent(translator.getGraph());
			
			JOptionPane.showMessageDialog(null, "Translation executed successfully!", "INFO", JOptionPane.INFORMATION_MESSAGE);
		}
		else if(i.intValue() == Translator.ERROR){
			//chiudiamo il frame con la barra grafica di avanzamento e abilitiamo il frame corrente
			this.progressFrame.dispose();
			this.setEnabled(true);
			
			JOptionPane.showMessageDialog(null, "Error occurred during translation!", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void miSelectSourceFile_ActionPerformed(java.awt.event.ActionEvent event){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(new JavaFilter());
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setDialogTitle("SELECT SOURCE FILE");
		fileChooser.setApproveButtonText("Select");

		Dimension apriSize = fileChooser.getPreferredSize();
		Dimension frmSize = getSize();
		Point loc = getLocation();
		fileChooser.setLocation((frmSize.width - apriSize.width) / 2
				+ loc.x, (frmSize.height - apriSize.height) / 2 + loc.y);

		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			sourceFile = fileChooser.getSelectedFile();
			textFieldSourceFile.setText(sourceFile.toString());
		}
	}

	public void miSelectDestFile_ActionPerformed(java.awt.event.ActionEvent event){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(new JavaFilter());
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setDialogTitle("SELECT DESTINATION FILE");
		fileChooser.setApproveButtonText("Select");

		Dimension apriSize = fileChooser.getPreferredSize();
		Dimension frmSize = getSize();
		Point loc = getLocation();
		fileChooser.setLocation((frmSize.width - apriSize.width) / 2
				+ loc.x, (frmSize.height - apriSize.height) / 2 + loc.y);

		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			String fileName = selectedFile.toString();
			if(!fileName.endsWith(".java") && !fileName.endsWith(".JAVA")){
				fileName += ".java";
			}
			destFile = new File(fileName);
			textFieldDestFile.setText(destFile.toString());
		}
	}
	
	public void miStartTranslation_ActionPerformed(java.awt.event.ActionEvent event){
		if(destFile == null || sourceFile == null){
			JOptionPane.showMessageDialog(null, "Select source and destination file!", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		else{
			if(destFile.toString().equalsIgnoreCase(sourceFile.toString())){
				JOptionPane.showMessageDialog(null, "Select different source and destination file!", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
			else{
				int scelta1 = JOptionPane.showConfirmDialog(null, "Do you want to start the translation ?", "CONFIRM", JOptionPane.YES_NO_OPTION);
				if(scelta1 == JOptionPane.YES_OPTION){
					int scelta2 = 0;
					if(destFile.exists()){
						scelta2 = JOptionPane.showConfirmDialog(null, "Destination file (\"" + destFile.toString() + "\") already exists. Overwrite it ?", "CONFIRM", JOptionPane.YES_NO_OPTION);
					}
					if( (!destFile.exists()) ||
							(destFile.exists() && scelta2 == JOptionPane.YES_OPTION) ){
						
						//disabilitiamo il frame corrente
						this.setEnabled(false);

						//inizializziamo il frame con la barra grafica di avanzamento
						progressFrame = new JFrame();
						progressFrame.setTitle("Loading...");
						progressFrame.setSize(200, 60);
						progressFrame.setVisible(true);
						progressFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
						progressFrame.getContentPane().setLayout(new BorderLayout());
						JProgressBar progressBar = new JProgressBar(0, 100);
						progressBar.setValue(0);
						progressBar.setIndeterminate(true);
						progressFrame.getContentPane().add(progressBar, BorderLayout.CENTER);
						progressFrame.validate();
						setLocationOfFrame(progressFrame);

						if(radioKeyStatements.isSelected()){
							//avviamo la traduzione basata sui key statements
					        SwingWorker sw = new SwingWorker<Void, Void>(){ //necessario per la barra di avanzamento grafica
					        	public Void doInBackground(){
					        		translator.translate(Translator.KEY_STATEMENTS_BASED, sourceFile, destFile);
							        return null;
					        	}
					        };
					        sw.execute();
						}
						else if(radioFull.isSelected()){							
							//avviamo la traduzione di tipo FULL, cioè basata su ogni istruzione
					        SwingWorker sw = new SwingWorker<Void, Void>(){ //necessario per la barra di avanzamento grafica
					        	public Void doInBackground(){
					        		translator.translate(Translator.FULL, sourceFile, destFile);
							        return null;
					        	}
					        };
					        sw.execute();
						}
					}
				}
			}
		}
	}
	
	public void miAbout_ActionPerformed(java.awt.event.ActionEvent event) {
		JOptionPane.showMessageDialog(null, "DSC* TRANSLATOR\nby G. Fortino, F. Rango\n© 2010",
				"About", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void miOpenDSCStar_ActionPerformed(java.awt.event.ActionEvent event) {
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setMultiSelectionEnabled(false);
		fc.setAcceptAllFileFilterUsed(false);
		fc.setDialogTitle("OPEN");
		fc.setApproveButtonText("Open");
		GraphicDSCStarFilter xmlFilter = new GraphicDSCStarFilter(".xml", "XML File (.xml)");
		fc.addChoosableFileFilter(xmlFilter);
		fc.setFileFilter(xmlFilter);
		int rc = fc.showOpenDialog(this);
		if (rc != JFileChooser.APPROVE_OPTION) {
			return;
		}
		else if (this.graphComponent != null &&
				JOptionPane.showConfirmDialog(null, "Overwrite existing graphic DSC* ?", "CONFIRM",
						JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
			return;
		}
		try{
			mxGraph graph = new mxGraph();
			Document document = mxUtils.parseXml(mxUtils.readFile(fc.getSelectedFile().getAbsolutePath()));
			mxCodec codec = new mxCodec(document);
			codec.decode(document.getDocumentElement(), graph.getModel());
			
			//aggiorniamo il DSC* grafico
			initGraphComponent(graph);
		}
		catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.toString(), "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void miSaveDSCStar_ActionPerformed(java.awt.event.ActionEvent event) {
		mxGraph graph = this.graphComponent.getGraph();
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setMultiSelectionEnabled(false);
		fc.setAcceptAllFileFilterUsed(false);
		fc.setDialogTitle("SAVE");
		fc.setApproveButtonText("Save");
		GraphicDSCStarFilter defaultFilter = null;
		for (int i = 0; i < GraphicDSCStarFilter.formats.length; i++) {
			String ext = GraphicDSCStarFilter.formats[i];
			GraphicDSCStarFilter currentFilter = new GraphicDSCStarFilter(
					"." + ext.toLowerCase(), ext + " File" + " (."
							+ ext.toLowerCase() + ")");
			fc.addChoosableFileFilter(currentFilter);
			if (ext.equalsIgnoreCase("BMP")) {
				defaultFilter = currentFilter;
			}
		}
		fc.setFileFilter(defaultFilter);
		int rc = fc.showOpenDialog(this);
		if (rc != JFileChooser.APPROVE_OPTION) {
			return;
		}
		String filename = fc.getSelectedFile().toString();
		GraphicDSCStarFilter selectedFilter = (GraphicDSCStarFilter) fc.getFileFilter();
		String ext = selectedFilter.getExtension();
		if (!filename.toLowerCase().endsWith(ext.toLowerCase())) {
			filename += ext;
		}
		if (new File(filename).exists()
				&& JOptionPane.showConfirmDialog(null, "Destination file (\""
						+ fc.getSelectedFile().toString()
						+ "\") already exists. Overwrite it ?", "CONFIRM",
						JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
			return;
		}
		try {
			String ext2 = filename.substring(filename.lastIndexOf('.') + 1); //estensione senza punto
			if (ext2.equalsIgnoreCase("xml")){
				mxCodec codec = new mxCodec();
				String xml = mxUtils.getXml(codec.encode(graph.getModel()));
				mxUtils.writeFile(xml, filename);
			}
			else{
				Color bg = graphComponent.getBackground();
				BufferedImage image = mxCellRenderer.createBufferedImage(graph,
						null, 1, bg, graphComponent.isAntiAlias(), null,
						graphComponent.getCanvas());
				if (image != null) {
					ImageIO.write(image, ext2, new File(filename));
				}
				else {
					JOptionPane.showMessageDialog(null, "No image data!", "ERROR",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.toString(), "ERROR",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void miUndo_ActionPerformed(java.awt.event.ActionEvent event){
		undoManager.undo();
	}
	
	public void miRedo_ActionPerformed(java.awt.event.ActionEvent event){
		undoManager.redo();
	}

	class MenuListener implements java.awt.event.ActionListener {
		public void actionPerformed(java.awt.event.ActionEvent event) {
			Object object = event.getSource();
			if (object == miOpenDSCStar)
				miOpenDSCStar_ActionPerformed(event);
			else if (object == miSaveDSCStar)
				miSaveDSCStar_ActionPerformed(event);
			else if (object == miSelectSourceFile)
				miSelectSourceFile_ActionPerformed(event);
			else if (object == miSelectDestFile)
				miSelectDestFile_ActionPerformed(event);
			else if (object == miStartTranslation)
				miStartTranslation_ActionPerformed(event);
			else if (object == miUndo)
				miUndo_ActionPerformed(event);
			else if (object == miRedo)
				miRedo_ActionPerformed(event);
			else if (object == miAbout)
				miAbout_ActionPerformed(event);
		}
	}

}
