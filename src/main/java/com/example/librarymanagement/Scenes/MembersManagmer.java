package com.example.librarymanagement.Scenes;

import DatabaseManagment.Author.Author;
import DatabaseManagment.Books.Book;
import DatabaseManagment.Members.Member;
import com.example.librarymanagement.Controller;
import com.example.librarymanagement.LoginPage;
import components.CustomTable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MembersManagmer extends VBox {
    Label messageOp;
    Label PinMember;
    HBox PinContainer;
    HBox deleteContainer;
    Button reset;
    HBox editContainer;
    Label AddMem;
    Label titLable;
    List<Member> members_lsit;
    CustomTable table;
    TextField MemberID;
    int member_id;
    TextField MemberFirstName;
    String member_firstname;
    TextField MemberLastName;
    String member_lastname;
    TextField MemberAddress;
    String member_address;
    TextField MemberLibraryNum;
    Label message;
    int member_librarynum;
    Pane container;
    Button AddAuth;
    boolean PinToggel;
    HBox form_container;
    String addStyle="-fx-background-color: white;" +
            "-fx-pref-width: 300;" +
            "-fx-pref-height: 250;" +
            "-fx-background-radius: 10;";
    Label DeleteBook;
    Label EditBook;
    String[] memberManagerList;
    String[] messageList;


    String Css= LoginPage.class.getResource("Components.css").toExternalForm();

    public MembersManagmer(Controller controller,double width)  {
        String language = controller.getLanguage();
        if (language.equals("English")) {
            messageList = new String[]{"cannot delete this member before \nhe returns the book ","Library number must be a number","Error : please complete the form ",
                    "Error : this member is already exist ","new member added successfully","Error : author doesn't exist"};
        } else {
            messageList = new String[]{"Impossible de supprimer ce membre avant qu'il ne rende le livre","Le numéro de la bibliothèque doit être un nombre","Erreur : veuillez remplir le formulaire",
                    "Erreur : ce membre existe déjà","Nouveau membre ajouté avec succès","Erreur : l'auteur n'existe pas"};
        }
        if (language.equals("English")) {
            memberManagerList = new String[]{"ADD Member","Search","Add member","Delete","Pin","Edit","EDIT BOOK","Member Id","Enter member Id",
                    "First Name","Enter member first name","Last Name","Enter member last name","Address","Enter member address",
                    "Library Number","Submit","Reset"};
        } else {
            memberManagerList = new String[]{"AJOUTER MEMBRE","Rechercher","Ajouter un membre","Supprimer","Épingle","Modifier","MODIFIER LIVRE","Identifiant du membre","Entrez l'identifiant du membre",
                    "Prénom","Entrez le prénom du membre","Nom de famille","Entrez le nom de famille du membre","Adresse","Entrez l'adresse du membre",
                    "Numéro de bibliothèque","Soumettre","Réinitialiser"};
        }

        PinToggel=false;
        container=new Pane();
        message = new Label();
        try {
            members_lsit =controller.getAllMembers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setPrefWidth(width-200.0);





        VBox member_form= new VBox(10);
        member_form.setPrefWidth(200);
        member_form.setAlignment(Pos.CENTER_LEFT);
        HBox buttons= new HBox(10);


        HBox title = new HBox();
        title.setAlignment(Pos.CENTER);
        title.setPrefWidth(300);
        titLable = new Label(memberManagerList[0]);
        if(controller.getLanguage().equals("English"))
            titLable.setStyle("-fx-font-size: 30;" +
                    "-fx-text-fill: #000000;" +
                    "-fx-font-weight: bold");
        else
            titLable.setStyle("-fx-font-size: 20;" +
                    "-fx-text-fill: #000000;" +
                    "-fx-font-weight: bold");


        title.getChildren().add(titLable);
//        HBox buttons= new HBox(10);

        HBox options =new HBox(10);
        options.getStylesheets().add(Css);
        options.getStyleClass().add("options");
        options.setPrefWidth(width-200);
        TextField search = new TextField();
        search.setOnKeyReleased(e->{
            System.out.println(search.getText());
            List<Member> filtered_list = new ArrayList<Member>();
            for (Member member:
                    members_lsit) {
                int count =0;
                for (int i=0;i<search.getText().length()&&search.getText().length()<=member.getMember_Firstname().length();i++){
                    if ((search.getText().charAt(i)==member.getMember_Firstname().charAt(i))){
                        count++;
                    }
                }
                if (count==search.getText().length())
                    filtered_list.add(member);
                else{
                    count =0;
                    for (int i=0;i<search.getText().length()&&search.getText().length()<=String.valueOf(member.getMember_id()).length();i++){
                        if ((search.getText().charAt(i)==String.valueOf(member.getMember_id()).charAt(i))){
                            count++;
                        }
                    }
                    if (count==search.getText().length())
                        filtered_list.add(member);

                }
                table.UpdateTable(controller,null,filtered_list,member_form);
            }});

//        search.setLayoutX(0);
        search.setPromptText(memberManagerList[1]);
        search.getStyleClass().clear();
        search.getStyleClass().add("Search");
        if(controller.getLanguage().equals("English"))
            search.setTranslateX(150);
        else
            search.setTranslateX(20);
        HBox addContainer =new HBox(2);
        addContainer.setAlignment(Pos.CENTER);
        AddMem = new Label(memberManagerList[2]);
        Label Plus = new Label("+");
        AddMem.setAlignment(Pos.CENTER);
        Plus.setAlignment(Pos.CENTER);
        Plus.setStyle("-fx-text-fill: #07c907;" +
                "-fx-font-size: 25px;" +
                "-fx-font-weight: bold;" +
                "-fx-translate-y: -2");
        addContainer.getChildren().add(Plus);
        addContainer.getChildren().add(AddMem);

        AddMem.getStyleClass().add("AddBook");
        AddMem.setOnMouseClicked(e->{
            editContainer.setDisable(true);
            deleteContainer.setDisable(true);
            titLable.setText(memberManagerList[0]);
            int MemberID_indx = 0;
            for (int i=1;i<member_form.getChildren().size()-2;i++){
                if(i%2==0){
                    TextField textField= (TextField)member_form.getChildren().get(i);
                    if (MemberID_indx==0)
                        textField.setText(String.valueOf(member_id));
                    else
                        textField.setText("");
                    MemberID_indx++;
                }
            }
            MemberLibraryNum.setText(String.valueOf(controller.getLibrary_number()));
            MemberLibraryNum.setDisable(true);
            member_librarynum=controller.getLibrary_number();
            reset.setVisible(false);
            form_container.setStyle(addStyle+"-fx-effect: dropshadow(three-pass-box, rgba(33,33,33,0.11), 10, 0, 0, 0);");
//            AuthorSection.setEffect();
            table.setEffect(new GaussianBlur());

            container.getChildren().add(form_container);
        });

        DeleteBook = new Label(memberManagerList[3]);
        DeleteBook.setAlignment(Pos.CENTER);
        deleteContainer = new HBox(2);
        deleteContainer.setAlignment(Pos.CENTER);
        FileInputStream delS;
        try {
            URL delsUrl= new URL(LoginPage.class.getResource("/pics/minus.png").toExternalForm());
            delS = new FileInputStream(delsUrl.getPath());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        Image delimage =new Image(delS);
        ImageView delimageView =new ImageView(delimage);
        delimageView.setFitWidth(14);
        delimageView.setFitHeight(14);
        Label del = new Label("_");
        del.setAlignment(Pos.CENTER);
        del.setStyle("-fx-text-fill: #e01f1f;" +
                "-fx-font-size: 30px;" +
                "-fx-font-weight: bold");
        deleteContainer.getChildren().add(delimageView);
        deleteContainer.getChildren().add(DeleteBook);

        PinMember = new Label(memberManagerList[4]);
        PinMember.getStyleClass().add("AddBook");
        PinMember.setAlignment(Pos.CENTER);
        PinContainer = new HBox(2);
        PinContainer.setAlignment(Pos.CENTER);
        FileInputStream PinF;
        try {
            URL delsUrl= new URL(LoginPage.class.getResource("/pics/pin.png").toExternalForm());
            PinF = new FileInputStream(delsUrl.getPath());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        Image Pinimage =new Image(PinF);
        ImageView PinimageView =new ImageView(Pinimage);
        PinimageView.setFitWidth(14);
        PinimageView.setFitHeight(14);
        PinContainer.getChildren().add(PinimageView);
        PinContainer.getChildren().add(PinMember);

        PinContainer.setDisable(true);
        PinMember.setOnMouseClicked(e->{
            if(!PinToggel)
                controller.setPinedMem(true);
            else
                controller.setPinedMem(false);
            PinToggel=!PinToggel;

        });



        deleteContainer.setDisable(true);
        DeleteBook.setOnMouseClicked(e->{

            System.out.println("delete");
            boolean res =controller.deleteMember(controller,member_form);
            if (!res){



                messageOp.setText(messageList[0]);
                messageOp.setTextFill(Paint.valueOf("#fc3232"));

            }
            for (int i=1;i<member_form.getChildren().size()-2;i++){
                if ((i%2==0)){

                    TextField textField= (TextField)member_form.getChildren().get(i);
                    textField.setText("");
                }
            }
//                controller.d(M.getBookId(), book.getBookAuth(),controller);
        });

        DeleteBook.getStyleClass().add("AddBook");
        editContainer = new HBox(2);
        editContainer.setAlignment(Pos.CENTER);
        FileInputStream fileInputStream;
        try {
            URL delsUrl= new URL(LoginPage.class.getResource("/pics/pencil.png").toExternalForm());
            fileInputStream = new FileInputStream(delsUrl.getPath());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        Image image =new Image(fileInputStream);
        ImageView imageView =new ImageView(image);
        imageView.setFitHeight(14);
        imageView.setFitWidth(14);

        EditBook = new Label(memberManagerList[5]);

        editContainer.getChildren().add(imageView);

        editContainer.getChildren().add(EditBook);
        editContainer.setDisable(true);
        EditBook.setOnMouseClicked(e->{
            titLable.setText(memberManagerList[6]);
            form_container.setStyle(addStyle+"-fx-effect: dropshadow(three-pass-box, rgba(33,33,33,0.11), 10, 0, 0, 0);");
//            AuthorSection.setEffect();
            table.setEffect(new GaussianBlur());
            container.getChildren().add(form_container);
            MemberLibraryNum.setText(String.valueOf(controller.getLibrary_number()));
            MemberLibraryNum.setDisable(true);
        });
        EditBook.getStyleClass().add("AddBook");

        messageOp = new Label();
        messageOp.setMaxWidth(300);
        messageOp.setPrefWidth(300);

        options.getChildren().add(addContainer);
        options.getChildren().add(editContainer);
        options.getChildren().add(deleteContainer);
        options.getChildren().add(PinContainer);
        options.getChildren().add(messageOp);
        options.getChildren().add(search);
        table = new CustomTable(controller,width,options,null,members_lsit,member_form);
        form_container= new HBox(10);
        table.setOnMouseClicked(e->{

            container.getChildren().remove(form_container);
            table.setEffect(null);
        });
        form_container.setStyle(addStyle);
        form_container.setLayoutX(200);
        form_container.setLayoutY(20);
        form_container.setAlignment(Pos.CENTER);
        Label idLable=new Label(memberManagerList[7]);

        MemberID = new TextField(String.valueOf(controller.NewMemberId()));
        member_id=controller.NewMemberId();
        MemberID.setText(String.valueOf(member_id));
        MemberID.getStyleClass().add("textInput");
        MemberID.setPromptText(memberManagerList[8]);
        MemberID.setOnKeyReleased(e->{
            if (!MemberID.getText().equals("")){
                member_id=Integer.parseInt(MemberID.getText());
            }
        });
        Label FNameLable=new Label(memberManagerList[9]);

        MemberFirstName = new TextField();
        MemberFirstName.getStyleClass().add("textInput");
        MemberFirstName.setPromptText(memberManagerList[10]);
        MemberFirstName.setOnKeyReleased(e->{
            member_firstname= MemberFirstName.getText();
        });
        Label LNameLable=new Label(memberManagerList[11]);

        MemberLastName = new TextField();
        MemberLastName.getStyleClass().add("textInput");
        MemberLastName.setPromptText(memberManagerList[12]);
        MemberLastName.setOnKeyReleased(e->{
            member_lastname= MemberLastName.getText();
        });
        Label addrLable=new Label(memberManagerList[13]);
        MemberAddress = new TextField();
        MemberAddress.getStyleClass().add("textInput");
        MemberAddress.setPromptText(memberManagerList[14]);
        MemberAddress.setOnKeyReleased(e->{
            member_address= MemberAddress.getText();
        });

        Label libLable=new Label(memberManagerList[15]);
        System.out.println("sssssssssssssssssss"+controller.getLibrary_number());
        String libnum=String.valueOf(controller.getLibrary_number());
        MemberLibraryNum = new TextField();
//        MemberLibraryNum.
//        MemberLibraryNum.setText();
//        MemberLibraryNum.setDisable(true);
        MemberLibraryNum.getStyleClass().add("textInput");
//        MemberLibraryNum.setPromptText("Enter member library number");
        MemberLibraryNum.setOnKeyReleased(e->{
            if (!MemberLibraryNum.getText().equals("")){
                try{
                    member_librarynum= Integer.parseInt(MemberLibraryNum.getText());
                }catch (Exception es){
                    message.setText(messageList[1]);
                    message.setTextFill(Paint.valueOf("#FF0000FF"));
                }
            }
        });
        Button submit = new Button(memberManagerList[16]);
        reset = new Button(memberManagerList[17]);
        reset.setVisible(false);
        reset.setOnAction(e->{
            for (int i=1;i<member_form.getChildren().size()-2;i++){
                if(i%2==0){
                    TextField textField= (TextField)member_form.getChildren().get(i);
                    textField.setText("");}}
            submit.setText(memberManagerList[16]);
            reset.setVisible(false);
        });
        submit.setOnAction(e->{
            List<Member> members_lsit = null;
            try {
                members_lsit = controller.getAllMembers();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            if (submit.getText().equals(memberManagerList[16])){
                Author author=null;
                if ((member_id == 0) || member_firstname.isEmpty() || member_lastname.isEmpty() || member_address.isEmpty() || (member_librarynum==0)){
                    message.setText(messageList[2] );
                    message.setTextFill(Paint.valueOf("#FF0000FF"));
                    return;
                }
                for (Member member:
                        members_lsit) {
                    if (member.getMember_id()==member_id){
                        message.setText(messageList[3]);
                        MemberID.setBorder(Border.stroke(Paint.valueOf("#FF0000FF")));
                        message.setTextFill(Paint.valueOf("#FF0000FF"));
                        return;
                    }
                }

                member_firstname=MemberFirstName.getText();
                Member  member =new Member(member_id,member_firstname,member_lastname,member_address,member_librarynum);
                boolean state= controller.AddMember(member);
                if (state){
                    message.setText( messageList[4]);
                    message.setTextFill(Paint.valueOf("#84EE60FF"));
                    try {
                        table.UpdateTable(controller,null,controller.getAllMembers(),member_form);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
//                table.addMemberRow(member,controller,member_form);
                }
                else {
                    message.setText(messageList[5]);
                    message.setTextFill(Paint.valueOf("#FF0000FF"));

                }
            }else
            {
                member_id = Integer.parseInt(MemberID.getText());
                member_firstname= MemberFirstName.getText();
                member_lastname= MemberLastName.getText();
                member_address= MemberAddress.getText();
                System.out.println(controller.getLibrary_number());
                member_librarynum= controller.getLibrary_number();
                Member  member =new Member(member_id,member_firstname,member_lastname,member_address,member_librarynum);
                controller.updateMember(member);
                try {
                    table.UpdateTable(controller,null,controller.getAllMembers(),member_form);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        ScrollPane scrollPane =new ScrollPane();
        scrollPane.setStyle("-fx-focus-color:transparent;-fx-border-width: 0");
        scrollPane.setPrefSize(width-200, 500);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setContent(table);
//        scrollPane.setPrefSize(width,600);
        scrollPane.setFitToHeight(true);
        member_form.getChildren().add(title);
        member_form.getChildren().add(idLable);
        member_form.getChildren().add(MemberID);
        member_form.getChildren().add(FNameLable);
        member_form.getChildren().add(MemberFirstName);
        member_form.getChildren().add(LNameLable);
        member_form.getChildren().add(MemberLastName);
        member_form.getChildren().add(addrLable);
        member_form.getChildren().add(MemberAddress);
        member_form.getChildren().add(libLable);
        member_form.getChildren().add(MemberLibraryNum);
        buttons.getChildren().add(submit);
        buttons.getChildren().add(reset);
        member_form.getChildren().add(buttons);
        form_container.getChildren().add(member_form);
        member_form.getChildren().add(message);
        container.getChildren().add(scrollPane);
        getChildren().add(options);
        getChildren().add(container);

    }
}
