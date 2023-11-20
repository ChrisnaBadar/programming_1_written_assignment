import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Main class for the Quiz Program
public class QuizProgram {
    // GUI components
    private JFrame frame;
    private JPanel panel;
    private JLabel questionHeader;
    private JLabel questionLabel;
    private ButtonGroup buttonGroup;
    private JRadioButton[] options;
    private JButton submitButton;
    private JLabel title;
    private JLabel subtitle;

    // Questions and their respective correct answers
    private String[] questionsHeaders = {
            "1. Lightsaber Basics:",
            "2. Force Sensitivity:",
            "3. Jedi Code:",
            "4. Galactic History:",
            "5. Mind Control:"
    };
    private String[] questions = {
            "What is the primary function of a lightsaber crystal in constructing a lightsaber?",
            "Which of the following is a common early sign of Force sensitivity in individuals?",
            "Complete the Jedi Code: \"There is no emotion, there is ________.\"",
            "During which conflict did the Jedi fight against the Sith and their droid army?",
            "What Force power allows a Jedi to influence and manipulate the thoughts of others?"
    };

    private String[][] optionsArray = {
        {"A. Powering the hyperdrive","B. Focusing the Force", "C. Generating shields", "D. Providing navigation"},
        {"A. Exceptional piloting skills", "B. Extraordinary strength", "C. Exceptional cooking abilities", "D. Exceptional mathematical skills"},
        {"A. only peace", "B. only hatred", "C. only power", "D. only fear"},
        {"A. Clone Wars", "B. Mandalorian Wars", "C. Great Hyperspace War", "D. Yuuzhan Vong War"},
        {"A. Force Push", "B. Mind Trick", "C. Force Lightning", "D. Telekinesis"}
    };
    

    private char[] correctAnswers = {'B', 'A', 'A', 'A', 'B'};

    private int currentQuestionIndex = 0;
    private int score = 0;

    // Constructor initializes the GUI components and sets up the frame
    public QuizProgram() {
        frame = new JFrame("Quiz Program");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(720, 480);        

        panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        title = new JLabel("Yoda Cadet Entrance Quiz");
        subtitle = new JLabel("This quiz will assess your worthiness to enter the Jedi Academy.");

        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setBorder(new EmptyBorder(20, 0, 20, 0));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitle.setBorder(new EmptyBorder(0, 0, 20, 0));
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(title);
        panel.add(subtitle);

        questionHeader = new JLabel(questionsHeaders[currentQuestionIndex]);
        questionLabel = new JLabel(questions[currentQuestionIndex]);

        questionHeader.setFont(new Font("Poppins", Font.BOLD, 18));

        panel.add(questionHeader);
        panel.add(questionLabel);

        buttonGroup = new ButtonGroup();
        options = new JRadioButton[4];

        for (int i = 0; i < options.length; i++) {
            options[i] = new JRadioButton(optionsArray[currentQuestionIndex][i]);
            buttonGroup.add(options[i]);
            panel.add(options[i]);
        }

        submitButton = new JButton("Submit");
        submitButton.setMargin(new Insets(0, 20, 0, 20));
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
            }
        });

        panel.add(submitButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    // Method to check the user's answer and handle the flow of the quiz
    private void checkAnswer() {
        char userAnswer = getSelectedAnswer();
    
        // Switch statement to handle user input
        switch (userAnswer) {
            case 'A':
            case 'B':
            case 'C':
            case 'D':
                // Check if the user's answer is correct
                if (userAnswer == correctAnswers[currentQuestionIndex]) {
                    score++;
                }
                break;
            default:
                // Handle invalid input (e.g., if the user doesn't select an option)
                JOptionPane.showMessageDialog(frame, "Please choose an option A, B, C, or D.");
                return;
        }
    
        // Move to the next question or show the result
        currentQuestionIndex++;
    
        if (currentQuestionIndex < questions.length) {
            updateQuestion();
        } else {
            showResult();
        }
    
        // Clear the selection of all radio buttons
        buttonGroup.clearSelection();
    }

    // Method to get the user's selected answer
    private char getSelectedAnswer() {
        for (int i = 0; i < options.length; i++) {
            if (options[i].isSelected()) {
                return (char) ('A' + i);
            }
        }
        return '\0';
    }

    // Method to update the displayed question and options
    private void updateQuestion() {
        questionLabel.setText(questions[currentQuestionIndex]);
        questionHeader.setText(questionsHeaders[currentQuestionIndex]);
        for (int i = 0; i < options.length; i++) {
            options[i].setText(optionsArray[currentQuestionIndex][i]);
            options[i].setSelected(false);
        }
    }

    // Method to display the final result of the quiz
    private void showResult() {
        double percentage = (double) score / questions.length * 100;
        String message;
    
        // Determine the appropriate message based on the user's score percentage
        if (percentage == 100.0) {
            message = "May the Force be with you! You have shown exceptional knowledge and are destined for greatness as a Jedi Knight.";
        } else if (percentage >= 80.0) {
            message = "Impressive, Padawan! Your connection with the Force is strong; continue your training diligently.";
        } else if (percentage >= 60.0) {
            message = "A promising start, young one. Keep honing your skills on the path to Jedi mastery.";
        } else if (percentage >= 40.0) {
            message = "Your path to becoming a Jedi is just beginning; focus and trust in the Force.";
        } else {
            message = "May you find balance and continue your journey in understanding the Force.";
        }
    
        // Display the final score and message in a JOptionPane
        String scoreMessage = String.format("Quiz completed! Your final score: %.2f%%\n%s", percentage, message);
        JOptionPane.showMessageDialog(frame, scoreMessage);
        
        // Close the application
        frame.dispose();
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new QuizProgram();
            }
        });
    }
}
