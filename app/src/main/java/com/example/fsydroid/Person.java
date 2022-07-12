package com.example.fsydroid;

public class Person {
    private Integer Id;
    private String Reference;
 /*   private String LastName;
    private String SecondLastName;
    private String Name;
    private String Type;
    private String Gender;
    private String BornDate;
    private String GroupName;
    private String GroupCounselor;
    private String CompanyName;
    private String CompanyCounselor;
    private String CabinName;
    private String CabinType;
    private String Insurance;
    private String MedicalInformation;
    private String PhysicalDifficulty;
    private String PhysicalTreatment;
    private String FoodInformation;
    private String StomachProblem;
    private String ColitisGastritis;
    private String DiabeticoAsmatico;*/

    public Person(Integer id, String reference) {
        Id = id;
        Reference = reference;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getReference() {
        return Reference;
    }

    public void setReference(String reference) {
        Reference = reference;
    }
}
