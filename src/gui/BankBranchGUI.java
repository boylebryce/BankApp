package gui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.FocusAdapter;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.jar.JarFile;

public class BankBranchGUI extends JFrame {
    private JPanel panel1;

    public BankBranchGUI() {
//        createUIComponents();

//        DefaultTableModel model = (DefaultTableModel) table1.getModel();
////        model.addRow(new Object[]{"1", "test", "test", "test"});
//        this.reviewHandler = reviewHandler;
//
//        setContentPane(panel1);
//        setVisible(true);
//        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        loadReviewsButton.addActionListener(e -> processLoadButtonAction());
//        searchButton.addActionListener(e -> processSearchButtonAction());
//        deleteButton.addActionListener(e -> processDeleteButtonAction());
    }

    private void createUIComponents() {
//        table1 = new JTable(new MovieReviewTableModel());
//        table1.getSelectionModel().addListSelectionListener(e -> deleteButton.setEnabled(table1.getSelectedRow() >= 0));
    }

    private void processLoadButtonAction() {
//        JFileChooser fileChooser = new JFileChooser();
//        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
//        int result = fileChooser.showOpenDialog(null);
//        if (result == JFileChooser.APPROVE_OPTION) {
//            File file = fileChooser.getSelectedFile();
//            int realClass = -1;
//            if (positiveRadioButton.isSelected()) {
//                realClass = 0;
//            }
//            else if (negativeRadioButton.isSelected()) {
//                realClass = 1;
//            }
//            else {
//                realClass = 2;
//            }
//            reviewHandler.loadReviews(file.getPath(), realClass);
//        }
    }

    void processSearchButtonAction() {
//        Collection<MovieReview> foundReviews;
//        if (IDRadioButton.isSelected()) {
//            MovieReview movieReview = reviewHandler.searchById(Integer.parseInt(textField1.getText()));
//            foundReviews = movieReview == null ? Collections.emptyList() : Collections.singleton(movieReview);
//        }
//        else {
//            foundReviews = reviewHandler.searchBySubstring(textField1.getText());
//            if (foundReviews == null) {
//                foundReviews = Collections.emptyList();
//            }
//        }
//
//        MovieReviewTableModel model = (MovieReviewTableModel)table1.getModel();
//        model.clear();
//        for (MovieReview movieReview : foundReviews) {
//            model.addRow(movieReview);
//        }
    }

    private void processDeleteButtonAction() {
//        MovieReviewTableModel model = (MovieReviewTableModel) table1.getModel();
//        int id = (int)model.getValueAt(table1.getSelectedRow(), 0);
//        reviewHandler.deleteReview(id);
//        model.removeRow(id);
    }

//    static class MovieReviewTableModel extends AbstractTableModel {
//        private List<MovieReview> reviews;
//
//        public MovieReviewTableModel() {
//            reviews = new ArrayList<>();
//        }
//
//        @Override
//        public String getColumnName(int columnIndex) {
//            switch (columnIndex) {
//                case 0:
//                    return "ID";
//                case 1:
//                    return "Text";
//                case 2:
//                    return "Predicted polarity";
//                case 3:
//                    return "Real polarity";
//            }
//            return null;
//        }
//
//        @Override
//        public int getRowCount() {
//            return reviews.size();
//        }
//
//        @Override
//        public int getColumnCount() {
//            return 4;
//        }
//
//        @Override
//        public Object getValueAt(int rowIndex, int columnIndex) {
//            MovieReview review = reviews.get(rowIndex);
//            switch (columnIndex) {
//                case 0:
//                    return review.getId();
//                case 1:
//                    return review.getText();
//                case 2:
//                    return MovieReview.getPolarityString(review.getPredictedPolarity());
//                case 3:
//                    return MovieReview.getPolarityString(review.getRealPolarity());
//            }
//            return null;
//        }
//
//        public void addRow(MovieReview review) {
//            int rowCount = getRowCount();
//            reviews.add(review);
//            fireTableRowsInserted(rowCount, rowCount);
//        }
//
//        public void removeRow(int id) {
//            int index = -1;
//            for (int i = 0; i<reviews.size(); i++) {
//                if (reviews.get(i).getId() == id) {
//                    index = i;
//                    break;
//                }
//            }
//            if (index >= 0) {
//                reviews.remove(index);
//                fireTableRowsDeleted(index, index);
//            }
//        }
//
//        public void clear() {
//            int rowCount = getRowCount();
//            if (rowCount > 0) {
//                reviews.clear();
//                fireTableRowsDeleted(0, rowCount-1);
//            }
//        }
//    }


}
