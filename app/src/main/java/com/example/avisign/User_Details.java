package com.example.avisign;


    public class User_Details {

        public String name,address,email,phone_number;
        User_Details(){

        }

        public User_Details(String name, String address,  String email, String phone_number) {
            this.name = name;
            this.address = address;
            //this.type = type;
            this.email = email;
            this.phone_number = phone_number;
        }

        public String getName() {
            return name;
        }

        public String getAddress() {
            return address;
        }

        //public String getType() {
        //   return type;
        //}

        public String getEmail() {
            return email;
        }

        public String getPhone_number() {
            return phone_number;
        }


}
