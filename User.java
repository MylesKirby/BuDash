package uk.co.myleskirby.UniProject;

public class User {
    private String name = "";
    private String gender= "";
    private String age= "";
    private String education= "";
    private String system= "";
    private String familiar= "";
    private String passUsed= "";
    private String passType= "";
    public String email= "";
    public String favPasscode= "";
    public String instructionOrder= "";

    public String easyAttempts= "0";
    public String easyCompleted= "False";
    public String mediumAttempts= "0";
    public String mediumCompleted= "False";
    public String hardAttempts= "0";
    public String hardCompleted= "False";

    public User() {

    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
    public User(String age, String education, String email, String familiar, String favPasscode, String gender, String instructionOrder, String name, String passType, String passUsed, String system) {
        this.age = age;
        this.education = education;
        this.name = name;
        this.email = email;
        this.familiar = familiar;
        this.favPasscode = favPasscode;
        this.gender = gender;
        this.instructionOrder = instructionOrder;
        this.passType = passType;
        this.passUsed = passUsed;
        this.system = system;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getFamiliar() {
        return familiar;
    }

    public void setFamiliar(String familiar) {
        this.familiar = familiar;
    }

    public String getPassUsed() {
        return passUsed;
    }

    public void setPassUsed(String passUsed) {
        this.passUsed = passUsed;
    }

    public String getPassType() {
        return passType;
    }

    public void setPassType(String passType) {
        this.passType = passType;
    }

    public String getFavPasscode() {
        return favPasscode;
    }

    public void setFavPasscode(String favPasscode) {
        this.favPasscode = favPasscode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstructionOrder() {
        return instructionOrder;
    }

    public String getEasyAttempts() {
        return easyAttempts;
    }

    public void setEasyAttempts(String age) {
        this.easyAttempts = easyAttempts;
    }

    public String getEasyCompleted() {
        return easyCompleted;
    }

    public void setEasyCompleted(String easyCompleted) {
        this.easyCompleted = easyCompleted;
    }

    public String getMediumAttempts() {
        return mediumAttempts;
    }

    public void setMediumAttempts(String mediumAttempts) {
        this.mediumAttempts = mediumAttempts;
    }

    public String getMediumCompleted() {
        return mediumCompleted;
    }

    public void setMediumCompleted(String mediumCompleted) {
        this.mediumCompleted = mediumCompleted;
    }

    public String getHardAttempts() {
        return hardAttempts;
    }

    public void setHardAttempts(String hardAttempts) {
        this.hardAttempts = hardAttempts;
    }

    public String getHardCompleted() {
        return hardCompleted;
    }

    public void setHardCompleted(String hardCompleted) {
        this.hardCompleted = hardCompleted;
    }

    public void setInstructionOrder(String instructionOrder) {
        this.instructionOrder = instructionOrder;
    }



}
