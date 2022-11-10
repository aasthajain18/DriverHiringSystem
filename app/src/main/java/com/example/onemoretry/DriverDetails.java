package com.example.onemoretry;

public class DriverDetails {
    String Name,Location,Pincode,ContactNo;

    public DriverDetails(){}
    public DriverDetails(String Name,String ContactNo,String Location,String Pincode){
        this.Name = Name;
        this.Location = Location;
        this.ContactNo = ContactNo;
        this.Pincode = Pincode;
    }

    public String getName() {
        return Name;
    }
    public String getCon() {
        return ContactNo;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public String getLoc() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getPin() {
        return Pincode;
    }

    public void setPincode(String pincode) {
        Pincode = pincode;
    }
}
