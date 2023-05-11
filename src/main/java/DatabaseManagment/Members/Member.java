package DatabaseManagment.Members;

public class Member {
        int member_id;
        String member_Firstname;
        String member_Lastname;
        String address;
        int library_num;

    public Member(int member_id, String member_Firstname, String member_Lastname, String address, int library_num) {
        this.member_id = member_id;
        this.member_Firstname = member_Firstname;
        this.member_Lastname = member_Lastname;
        this.address = address;
        this.library_num = library_num;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public String getMember_Firstname() {
        return member_Firstname;
    }

    public void setMember_Firstname(String member_Firstname) {
        this.member_Firstname = member_Firstname;
    }

    public String getMember_Lastname() {
        return member_Lastname;
    }

    public void setMember_Lastname(String member_Lastname) {
        this.member_Lastname = member_Lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getLibrary_num() {
        return library_num;
    }

    public void setLibrary_num(int library_num) {
        this.library_num = library_num;
    }
}
