package ui;

import exceptions.*;
import model.CookingUtensil;
import model.FoodItem;
import model.Item;
import model.Recipe;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

// Represents the ui of the application.
// Sources used: https://www.youtube.com/watch?v=Cxp_HvXZh6g
//               https://www.youtube.com/watch?v=Kmgo00avvEw
//               https://stackoverflow.com/questions/16343098/resize-a-picture-to-fit-a-jlabel
//               AlarmSystem Project
public class App {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static final int BUTTON_WIDTH = 100;
    private static final int BUTTON_HEIGHT = 50;
    private static final Border LINE_BORDER = new LineBorder(Color.BLACK, 3);
    private static final Font FONT = new Font("MV Boli", Font.PLAIN, 30);

    private AppFrame frame;
    private MealSuggesterApp app;
    private JTextArea promptField;
    private JLabel displayField;

    JPanel topButtonPanel;
    JPanel outputPanel;
    JPanel inputPanel;
    JPanel promptPanel;


    // EFFECTS: constructs the App UI.
    public App() {
        app = new MealSuggesterApp();

        init();

        frame = new AppFrame();
        frame.setSize(WIDTH, HEIGHT);
        frame.setLayout(null);

        frame.add(topButtonPanel);
        frame.add(outputPanel);
        frame.add(inputPanel);
        frame.add(promptPanel);
    }

    // Enum to determine what to output on output panel.
    public enum DisplayStatus {
        items,
        utensils,
        recipes,
    }

    // MODIFIES: this
    // EFFECTS: initializes all the panels in app.
    private void init() {
        topButtonPanel = new JPanel();
        topButtonPanel.setBounds(0, 0, WIDTH, HEIGHT / 4);
        topButtonPanel.setLayout(new BorderLayout());
        topButtonPanel.add(makeButtonPanel());

        outputPanel = new JPanel();
        outputPanel.setBackground(Color.LIGHT_GRAY);
        outputPanel.setBounds(WIDTH / 2, HEIGHT / 4, WIDTH / 2, HEIGHT / 2);
        outputPanel.setLayout(new BorderLayout());
        displayField = makeTitleLabel("", 30);

        inputPanel = new JPanel();
        inputPanel.setBackground(Color.green);
        inputPanel.setBounds(0, HEIGHT / 4, WIDTH / 2, HEIGHT / 2);
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(makeGridPanel());

        promptPanel = new JPanel();
        promptPanel.setBackground(Color.orange);
        promptPanel.setBounds(0, 400 + HEIGHT / 4, WIDTH, 200);
        promptPanel.setLayout(new GridLayout(1, 2, 3, 3));

        promptField = new JTextArea();
        initPromptField("Welcome!");

        promptPanel.add(promptField);
        promptPanel.add(displayImagePanel(DisplayStatus.items));
    }

    // MODIFIES: this
    // EFFECTS: refreshes the prompt panel.
    private void refreshPromptPanel(DisplayStatus displayStatus) {
        promptPanel.removeAll();
        promptPanel = new JPanel();
        promptPanel.setBackground(Color.orange);
        promptPanel.setBounds(0, 400 + HEIGHT / 4, WIDTH, 200);
        promptPanel.setLayout(new GridLayout(1, 2, 3, 3));
        promptPanel.add(promptField);
        promptPanel.add(displayImagePanel(displayStatus));
        frame.add(promptPanel);
    }

    // EFFECTS: displays the relevant image in current display status.
    private JPanel displayImagePanel(DisplayStatus displayStatus) {
        switch (displayStatus) {
            case items:
                return displayFoodImagePanel();
            case recipes:
                return displayRecipeImagePanel();
            case utensils:
                return displayUtensilImagePanel();
        }
        return null;
    }

    // EFFECTS: processes and returns an image of the food icon.
    private JPanel displayFoodImagePanel() {
        JPanel imagePanel = new JPanel();
        imagePanel.setBounds(0, 400 + HEIGHT / 4, WIDTH, 200);
        imagePanel.setLayout(new BorderLayout());
        try {
            BufferedImage recipeImage = ImageIO.read(new File("./data/food.jpg"));

            Image scaledImage = recipeImage.getScaledInstance(300, 100,
                    Image.SCALE_SMOOTH);

            ImageIcon imageIcon = new ImageIcon(scaledImage);

            JLabel picLabel = new JLabel(imageIcon);
            imagePanel.add(picLabel);
            imagePanel.setBorder(LINE_BORDER);

        } catch (IOException e) {
            System.out.println("IO Exception");
        }
        return imagePanel;
    }

    // EFFECTS: processes and returns an image of the cooking utensil icon.
    private JPanel displayUtensilImagePanel() {
        JPanel imagePanel = new JPanel();
        imagePanel.setBounds(0, 400 + HEIGHT / 4, WIDTH, 200);
        imagePanel.setLayout(new BorderLayout());
        try {
            BufferedImage recipeImage = ImageIO.read(new File("./data/utensil.jpg"));

            Image scaledImage = recipeImage.getScaledInstance(300, 100,
                    Image.SCALE_SMOOTH);

            ImageIcon imageIcon = new ImageIcon(scaledImage);

            JLabel picLabel = new JLabel(imageIcon);
            imagePanel.add(picLabel);
            imagePanel.setBorder(LINE_BORDER);

        } catch (IOException e) {
            System.out.println("IO Exception");
        }
        return imagePanel;
    }

    // EFFECTS: processes and returns an image of a recipe icon.
    private JPanel displayRecipeImagePanel() {
        JPanel imagePanel = new JPanel();
        imagePanel.setBounds(0, 400 + HEIGHT / 4, WIDTH, 200);
        imagePanel.setLayout(new BorderLayout());
        try {
            BufferedImage recipeImage = ImageIO.read(new File("./data/recipe.png"));

            Image scaledImage = recipeImage.getScaledInstance(150, 100,
                    Image.SCALE_SMOOTH);

            ImageIcon imageIcon = new ImageIcon(scaledImage);

            JLabel picLabel = new JLabel(imageIcon);
            imagePanel.add(picLabel);
            imagePanel.setBorder(LINE_BORDER);

        } catch (IOException e) {
            System.out.println("IO Exception");
        }
        return imagePanel;
    }

    // MODIFIES: this
    // EFFECTS: adds a display panel that will switch between displaying items and recipes.
    private JPanel addDisplayPanel(DisplayStatus displayStatus) {
        JPanel displayPanel = new JPanel();
        displayPanel.setBackground(Color.LIGHT_GRAY);
        displayPanel.setLayout(null);

        displayField.setBounds(0, 0, WIDTH / 2, 50);
        displayPanel.add(displayField);


        switch (displayStatus) {
            case items:
                JPanel itemDisplayPanel = makeItemDisplay();
                itemDisplayPanel.setBounds(0, 50, WIDTH / 2, HEIGHT / 2 - 50);
                displayField.setText("Displaying items!");
                displayPanel.add(itemDisplayPanel);
                break;
            case recipes:
                JPanel recipeDisplayPanel = makeGeneratedRecipeDisplay();
                recipeDisplayPanel.setBounds(0, 50, WIDTH / 2, HEIGHT / 2 - 50);
                displayField.setText("Displaying recipes!");
                displayPanel.add(recipeDisplayPanel);
                break;
        }

        displayPanel.revalidate();
        displayPanel.repaint();

        return displayPanel;
    }

    // MODIFIES: this
    // EFFECTS: refreshes the output in the output display panel.
    private void refreshOutputPanel(DisplayStatus displayStatus) {
        outputPanel.removeAll();
        outputPanel.setBackground(Color.LIGHT_GRAY);
        outputPanel.setBounds(WIDTH / 2, HEIGHT / 4, WIDTH / 2, HEIGHT / 2);
        outputPanel.setLayout(new BorderLayout());
        outputPanel.add(addDisplayPanel(displayStatus));
        frame.add(outputPanel);
    }

    // EFFECTS: returns a collection of recipe panels to display
    private JPanel makeGeneratedRecipeDisplay() {
        JPanel recipesDisplayPanel = new JPanel();
        List<Recipe> recipes = app.getUserInfo().generateRecipes();
        recipesDisplayPanel.setLayout(null);
        recipesDisplayPanel.setBounds(0, 0, WIDTH / 4, HEIGHT / 2 - 50);

        int count = 0;
        for (Recipe recipe : recipes) {
            JPanel recipeDisplayPanel = makeRecipeDisplayPanel(recipe, count);
            recipesDisplayPanel.add(recipeDisplayPanel);
            count++;
        }

        if (count == 0) {
            JLabel label = makeLabel("No recipes can be used; You need more items!", 0,
                    2, 15);
            label.setBounds(0, 100, WIDTH, 100);
            recipesDisplayPanel.add(label);
        }

        return recipesDisplayPanel;
    }

    // EFFECTS: reformats a recipe into a jpanel.
    private JPanel makeRecipeDisplayPanel(Recipe recipe, int pos) {
        JPanel recipeDisplayPanel = new JPanel();
        recipeDisplayPanel.setLayout(new GridLayout(3, 1, 5, 1));
        recipeDisplayPanel.setBounds(0, pos * 50, WIDTH / 2, HEIGHT / 2);
        recipeDisplayPanel.setSize(WIDTH / 2, 200);

        recipeDisplayPanel.add(makeLabel("Name:" + recipe.getName(), 1, 0, 15));

        JTextArea itemsTextArea = new JTextArea();
        itemsTextArea.setText(recipe.recipeItemsToString());
        itemsTextArea.setLineWrap(true);
        itemsTextArea.setWrapStyleWord(true);

        JTextArea instructionTextArea = new JTextArea();
        instructionTextArea.setText(recipe.instructionToOneString());
        instructionTextArea.setLineWrap(true);
        instructionTextArea.setWrapStyleWord(true);

        recipeDisplayPanel.add((makeTextAreaInputPanel("Items: ", itemsTextArea)));
        recipeDisplayPanel.add(makeTextAreaInputPanel("Instructions: ", instructionTextArea));

        recipeDisplayPanel.setBorder(LINE_BORDER);

        return recipeDisplayPanel;
    }

    // EFFECTS: returns an item display panel that contains all the item panel to add to another panel
    private JPanel makeItemDisplay() {
        JPanel itemDisplay = new JPanel();
        itemDisplay.setLayout(null);

        JPanel foodItemsDisplay = makeFoodItemsDisplay();
        foodItemsDisplay.setBounds(0, 0, WIDTH / 4, HEIGHT / 2 - 50);
        itemDisplay.add(foodItemsDisplay);

        JPanel cookingItemsDisplay = makeCookingItemsDisplay();
        cookingItemsDisplay.setBounds(WIDTH / 4, 0, WIDTH / 4, HEIGHT / 2 - 50);
        itemDisplay.add(cookingItemsDisplay);

        return itemDisplay;
    }

    // EFFECTS: returns the collection of food item panels.
    private JPanel makeFoodItemsDisplay() {
        JPanel foodDisplay = new JPanel();
        int numItems = app.getUserInfo().getFoodItems().size();

        foodDisplay.setSize(WIDTH / 2, HEIGHT / 8 * numItems);
        foodDisplay.setLayout(null);

        int count = 0;
        for (Item foodItem: app.getUserInfo().getFoodItems()) {
            foodDisplay.add(makeFoodItemDisplay((FoodItem) foodItem, count));
            count++;
        }

        return foodDisplay;
    }

    // EFFECTS: reformat and returns a food item in panel at given pos.
    private JPanel makeFoodItemDisplay(FoodItem foodItem, int pos) {
        JPanel foodItemPanel = new JPanel();
        foodItemPanel.setLayout(new GridLayout(2, 1, 5, 1));
        foodItemPanel.setBounds(0, pos * 50, WIDTH / 2, HEIGHT / 2);
        foodItemPanel.setSize(WIDTH / 4, 50);
        foodItemPanel.add(makeLabel("Name:" + foodItem.getName(), 1, 0, 15));
        foodItemPanel.add(makeLabel("Portions: " + foodItem.getPortions(),
                1, 0, 15));
        foodItemPanel.setBorder(LINE_BORDER);

        return foodItemPanel;
    }

    // EFFECTS: returns the collection of cooking utensil panels.
    private JPanel makeCookingItemsDisplay() {
        JPanel cookingDisplay = new JPanel();
        int numItems = app.getUserInfo().getCookingUtensils().size();

        cookingDisplay.setSize(WIDTH / 2, HEIGHT / 8 * numItems);
        cookingDisplay.setLayout(null);

        int count = 0;
        for (Item cookingUtensil: app.getUserInfo().getCookingUtensils()) {
            cookingDisplay.add(makeCookingItemDisplay((CookingUtensil) cookingUtensil, count));
            count++;
        }

        return cookingDisplay;
    }

    // EFFECTS: reformat and returns a cooking utensil in panel at given pos.
    private JPanel makeCookingItemDisplay(CookingUtensil cookingUtensil, int pos) {
        JPanel cookingItemPanel = new JPanel();
        cookingItemPanel.setLayout(new GridLayout(2, 1, 5, 1));
        cookingItemPanel.setBounds(0, pos * 50, WIDTH / 2, HEIGHT / 2);
        cookingItemPanel.setSize(WIDTH / 4, 50);
        cookingItemPanel.add(makeLabel("Name:" + cookingUtensil.getName(), 1, 0, 15));
        cookingItemPanel.add(makeLabel("Damaged: " + cookingUtensil.getIsDamaged(),
                1, 0, 15));
        cookingItemPanel.setBorder(LINE_BORDER);

        return cookingItemPanel;
    }

    // EFFECTS: returns the formatted button panel of the app.
    private JPanel makeButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 5, 5));
        buttonPanel.setBorder(new LineBorder(Color.BLACK, 3));
        buttonPanel.setBounds(0, 0, WIDTH, 200);
        buttonPanel.setBackground(Color.cyan);

        JButton button1 = new JButton(new ViewItemsAction());
        buttonPanel.add(button1);

        JButton button2 = new JButton(new GenerateRecipeAction());
        buttonPanel.add(button2);

        JButton button3 = new JButton(new SaveDataAction());
        buttonPanel.add(button3);

        JButton button4 = new JButton(new LoadDataAction());
        buttonPanel.add(button4);

        return buttonPanel;
    }

    // EFFECTS: returns the collection of add/remove item action panels
    private JPanel makeGridPanel() {
        JPanel gridPanel = new JPanel();
        gridPanel.setBounds(0, 0, WIDTH / 2, 200);
        gridPanel.setLayout(new GridLayout(2, 2, 5, 5));

        gridPanel.add(makeAddFoodItemPanel());
        gridPanel.add(makeAddCookingItemPanel());
        gridPanel.add(makeRemoveFoodItemPanel());
        gridPanel.add(makeRemoveCookingItemPanel());

        return gridPanel;
    }

    // EFFECTS: returns the panel where users can add food item
    private JPanel makeAddFoodItemPanel() {
        JPanel foodPanel = new JPanel();
        foodPanel.setBounds(0, 0, WIDTH / 4, 200);
        foodPanel.setBackground(Color.green);
        foodPanel.setBorder(LINE_BORDER);
        foodPanel.setLayout(new GridLayout(4, 1, 5, 5));

        JTextField nameField = new JTextField();
        nameField.setBounds(0,0, 50, 50);

        JTextField portionField = new JTextField();
        portionField.setBounds(0, 0, 50, 50);

        foodPanel.add(makeTitleLabel("Add Food Item!"));
        foodPanel.add(makeTextInputPanel("Name: ", nameField));
        foodPanel.add(makeTextInputPanel("Portions: ", portionField));

        JButton button = new JButton(new AddFoodItemAction(nameField, portionField));
        foodPanel.add(button);

        return foodPanel;
    }

    // EFFECTS: returns the panel where users can add cooking utensil
    private JPanel makeAddCookingItemPanel() {
        JPanel utensilPanel = new JPanel();
        utensilPanel.setBounds(0, 0, WIDTH / 4, 200);
        utensilPanel.setBackground(Color.pink);
        utensilPanel.setBorder(LINE_BORDER);
        utensilPanel.setLayout(new GridLayout(4, 1, 5, 5));

        JTextField nameField = new JTextField();
        nameField.setBounds(0,0, 50, 50);

        JCheckBox isDamagedCheckbox = new JCheckBox();

        utensilPanel.add(makeTitleLabel("Add Cooking Item!"));
        utensilPanel.add(makeTextInputPanel("Name: ", nameField));
        utensilPanel.add(makeBoxInputPanel("Damaged: ", isDamagedCheckbox));

        JButton button = new JButton(new AddCookingItemAction(nameField, isDamagedCheckbox));
        utensilPanel.add(button);

        return utensilPanel;
    }

    // EFFECTS: returns the panel where users can remove food item
    private JPanel makeRemoveFoodItemPanel() {
        JPanel foodPanel = new JPanel();
        foodPanel.setBounds(0, 0, WIDTH / 4, 200);
        foodPanel.setBackground(Color.red);
        foodPanel.setBorder(LINE_BORDER);
        foodPanel.setLayout(new GridLayout(4, 1, 5, 5));

        JTextField nameField = new JTextField();
        nameField.setBounds(0,0, 50, 50);

        JTextField portionField = new JTextField();
        portionField.setBounds(0, 0, 50, 50);

        foodPanel.add(makeTitleLabel("Remove Food Item"));
        foodPanel.add(makeTextInputPanel("Name: ", nameField));
        foodPanel.add(makeTextInputPanel("Portions: ", portionField));

        JButton button = new JButton(new RemoveFoodItemAction(nameField, portionField));
        foodPanel.add(button);

        return foodPanel;
    }

    // EFFECTS: returns the panel where users can remove cooking utensil
    private JPanel makeRemoveCookingItemPanel() {
        JPanel utensilPanel = new JPanel();
        utensilPanel.setBounds(0, 0, WIDTH / 4, 200);
        utensilPanel.setBackground(Color.yellow);
        utensilPanel.setBorder(LINE_BORDER);
        utensilPanel.setLayout(new GridLayout(4, 1, 5, 5));

        JTextField nameField = new JTextField();
        nameField.setBounds(0,0, 50, 50);

        JCheckBox isDamagedCheckbox = new JCheckBox();

        utensilPanel.add(makeTitleLabel("Remove Cooking Item"));
        utensilPanel.add(makeTextInputPanel("Name: ", nameField));
        utensilPanel.add(makeBoxInputPanel("Damaged: ", isDamagedCheckbox));

        JButton button = new JButton(new RemoveCookingItemAction(nameField, isDamagedCheckbox));
        utensilPanel.add(button);

        return utensilPanel;
    }

    // EFFECTS: returns a label at top, center position
    private JLabel makeTitleLabel(String label) {
        return makeLabel(label,1, 0, 15);
    }

    // EFFECTS: returns a label at top, center position with given size
    private JLabel makeTitleLabel(String label, int size) {
        return makeLabel(label,1, 0, size);
    }

    // EFFECTS: returns a label at given alignment.
    private JLabel makeLabel(String label, int verticalAlignment, int horizontalAlignment) {
        JLabel titleLabel = new JLabel();
        titleLabel.setText(label);
        titleLabel.setVerticalAlignment(verticalAlignment);
        titleLabel.setHorizontalAlignment(horizontalAlignment);

        titleLabel.setFont(new Font("MV Boli", Font.PLAIN, 15));

        return titleLabel;
    }

    // EFFECTS: returns a label at given alignment and with given size.
    private JLabel makeLabel(String label, int verticalAlignment, int horizontalAlignment, int size) {
        JLabel titleLabel = new JLabel();
        titleLabel.setText(label);
        titleLabel.setVerticalAlignment(verticalAlignment);
        titleLabel.setHorizontalAlignment(horizontalAlignment);

        titleLabel.setFont(new Font("MV Boli", Font.PLAIN, size));

        return titleLabel;
    }

    // EFFECTS: intializes the prompt field jtext area.
    private void initPromptField(String label) {
        promptField.setText(label);
        promptField.setFont(FONT);
        promptField.setBackground(Color.orange);
        promptField.setEditable(false);
        promptField.setLineWrap(true);
        promptField.setWrapStyleWord(true);
    }

    // EFFECTS: returns a panel with a label and text side by side.
    private JPanel makeTextInputPanel(String inputPrompt, JTextField textField) {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(1, 2, 5, 5));
        inputPanel.add(makeLabel(inputPrompt, 1, 2));
        inputPanel.add(textField);

        return inputPanel;
    }

    // EFFECTS: returns a panel with a label and checkbox side by side.
    private JPanel makeBoxInputPanel(String inputPrompt, JCheckBox checkBox) {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(1, 2, 5, 5));
        inputPanel.add(makeLabel(inputPrompt, 1, 2));

        inputPanel.add(checkBox);

        return inputPanel;
    }

    // EFFECTS: returns a panel with a label and text area side by side.
    private JPanel makeTextAreaInputPanel(String inputPrompt, JTextArea textArea) {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(1, 2, 5, 5));
        inputPanel.add(makeLabel(inputPrompt, 1, 2));
        inputPanel.add(textArea);

        return inputPanel;
    }

    // MODIFIES: this
    // EFFECTS: changes prompt output to given input
    private void setPrompt(String prompt) {
        promptField.setText(prompt);
    }

    // MODIFIES: this
    // EFFECTS: changes display text field output to given input
    private void setDisplayField(String displayText) {
        displayField.setText(displayText);
    }


    // Represents action when user wants to add a food item.
    private class AddFoodItemAction extends AbstractAction {
        private JTextField nameField;
        private JTextField portionField;

        private String name;
        private int portion;

        // EFFECTS: creates a new add food item action with given name text field and portion text field.
        AddFoodItemAction(JTextField nameField, JTextField portionField) {
            super("Add Food Item");
            this.nameField = nameField;
            this.portionField = portionField;
        }

        // MODIFIES: this
        // EFFECTS: attempts to add inputted item to user info.
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                portion = Integer.parseInt(portionField.getText());
                name = nameField.getText().toLowerCase();

                FoodItem foodItem = new FoodItem(name, portion);

                try {
                    foodItem.isValid();
                    app.getUserInfo().addFoodItem(foodItem);

                    nameField.setText("");
                    portionField.setText("");

                    refreshPromptPanel(DisplayStatus.items);
                    refreshOutputPanel(DisplayStatus.items);

                    setPrompt("Added " + portion + " portions \n of " + name + "!");


                } catch (InvalidFoodItemException ex) {
                    setPrompt("Invalid food item!");
                } catch (NegativePortionException negativePortionException) {
                    setPrompt("Please use a positive number for portion!");
                } catch (RuntimeException ex) {
                    setPrompt("Added " + portion + " portions of " + name + "!");
                    refreshOutputPanel(DisplayStatus.items);
                }

            } catch (NumberFormatException ex) {
                setPrompt("Enter valid number for portion pls");
            }

        }
    }

    // Represents action when user wants to add a cooking item.
    private class AddCookingItemAction extends AbstractAction {
        JTextField nameField;
        JCheckBox isDamagedField;

        private String name;
        private boolean isDamaged;

        // EFFECTS: creates a new add cooking item action with given name text field and portion text field.
        AddCookingItemAction(JTextField nameField, JCheckBox isDamagedField) {
            super("Add Cooking Item");
            this.nameField = nameField;
            this.isDamagedField = isDamagedField;
        }

        // MODIFIES: this
        // EFFECTS: attempts to add inputted item to user info.
        @Override
        public void actionPerformed(ActionEvent e) {
            isDamaged = isDamagedField.isSelected();
            name = nameField.getText().toLowerCase();
            CookingUtensil cookingUtensil = new CookingUtensil(name, isDamaged);

            try {
                cookingUtensil.isValid();
                app.getUserInfo().addCookingUtensil(cookingUtensil);

                isDamagedField.setSelected(false);
                nameField.setText("");

                refreshOutputPanel(DisplayStatus.items);
                refreshPromptPanel(DisplayStatus.utensils);

                setPrompt("Added " + name + "(Damaged: " + isDamaged + ")" + "!");
            } catch (InvalidCookingUtensilException ex) {
                setPrompt("Invalid cooking utensil!");
            }


        }
    }

    // Represents action when user wants to remove an item.
    private class RemoveFoodItemAction extends AbstractAction {
        private JTextField nameField;
        private JTextField portionField;

        private String name;
        private int portion;


        // EFFECTS: creates a new add food item action with given name text field and portion text field.
        RemoveFoodItemAction(JTextField nameField, JTextField portionField) {
            super("Remove Item");
            this.nameField = nameField;
            this.portionField = portionField;
        }

        // MODIFIES: this
        // EFFECTS: attempts to remove an item from user list.
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                portion = Integer.parseInt(portionField.getText());
                name = nameField.getText().toLowerCase();

                FoodItem foodItem = new FoodItem(name, portion);

                try {
                    foodItem.isValid();
                    app.getUserInfo().removeFoodItem(foodItem);

                    nameField.setText("");
                    portionField.setText("");

                    refreshOutputPanel(DisplayStatus.items);
                    refreshPromptPanel(DisplayStatus.items);
                    setPrompt("Removed " + portion + " portions of " + name + "!");

                } catch (InvalidFoodItemException ex) {
                    setPrompt("Invalid food item!");
                } catch (FoodItemNotInListException ex) {
                    setPrompt("Cannot remove item that is not in list!");
                } catch (NegativePortionException negativePortionException) {
                    setPrompt("Please input a positive integer for portion!");
                }

            }  catch (RuntimeException ex) {
                setPrompt("Enter valid number for portion pls");
            }
        }
    }

    // Represents action when user wants to remove an item.
    private class RemoveCookingItemAction extends AbstractAction {
        JTextField nameField;
        JCheckBox isDamagedField;

        private String name;
        private boolean isDamaged;

        // EFFECTS: creates a new add cooking item action with given name text field and portion text field.
        RemoveCookingItemAction(JTextField nameField, JCheckBox isDamagedField) {
            super("Remove Item");
            this.nameField = nameField;
            this.isDamagedField = isDamagedField;
        }

        // MODIFIES: this
        // EFFECTS: attempts to remove an item from user list.
        @Override
        public void actionPerformed(ActionEvent e) {
            isDamaged = isDamagedField.isSelected();
            name = nameField.getText().toLowerCase();
            CookingUtensil cookingUtensil = new CookingUtensil(name, isDamaged);

            try {
                cookingUtensil.isValid();
                try {
                    app.getUserInfo().removeCookingUtensil(cookingUtensil);
                    isDamagedField.setSelected(false);
                    nameField.setText("");

                    refreshOutputPanel(DisplayStatus.items);
                    refreshPromptPanel(DisplayStatus.utensils);
                    setPrompt("Removed " + name + "(Damaged: " + isDamaged + ")" + "!");
                } catch (CookingUtensilNotInListException ex) {
                    setPrompt("Cooking utensil is not in list!");
                }
            } catch (InvalidCookingUtensilException ex) {
                setPrompt("Invalid cooking utensil!");
            }
        }
    }

    // Represents action when user wants to view an item.
    private class ViewItemsAction extends AbstractAction {

        // EFFECTS: creates a new view item action.
        ViewItemsAction() {
            super("View Items");
        }

        // MODIFIES: this
        // EFFECTS: displays the items in the user's list.
        @Override
        public void actionPerformed(ActionEvent e) {
            setPrompt("Displaying current items in lists.");
            refreshOutputPanel(DisplayStatus.items);
            refreshPromptPanel(DisplayStatus.items);
        }
    }

    // Represents action when user wants to generate a recipe.
    private class GenerateRecipeAction extends AbstractAction {

        // EFFECTS: creates a new generate recipe action.
        GenerateRecipeAction() {
            super("Generate Recipes");
        }

        // MODIFIES: this
        // EFFECTS: displays the recipes that the user can possibly use.
        @Override
        public void actionPerformed(ActionEvent e) {
            setPrompt("Now displaying current recipes you can make!");
            refreshOutputPanel(DisplayStatus.recipes);
            refreshPromptPanel(DisplayStatus.recipes);
        }
    }

    // Represents action when user wants to save data.
    private class SaveDataAction extends AbstractAction {

        // EFFECTS: saves data
        SaveDataAction() {
            super("Save Data");
        }

        // MODIFIES: this
        // EFFECTS: saves the users items and lists.
        @Override
        public void actionPerformed(ActionEvent e) {
            app.saveUserInfo();
            setPrompt("Saved Data!");
        }
    }

    // Represents action when user wants to load data.
    private class LoadDataAction extends AbstractAction {

        // EFFECTS: creates a new load data action.
        LoadDataAction() {
            super("Load Data");
        }

        // MODIFIES: this
        // EFFECTS: loads the user's previously saved data.
        @Override
        public void actionPerformed(ActionEvent e) {
            app.loadUserInfo();
            setPrompt("Loaded Data");
            refreshOutputPanel(DisplayStatus.items);
        }
    }
}
