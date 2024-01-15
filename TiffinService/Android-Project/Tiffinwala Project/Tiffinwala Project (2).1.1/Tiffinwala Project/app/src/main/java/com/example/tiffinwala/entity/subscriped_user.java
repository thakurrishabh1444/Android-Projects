package com.example.tiffinwala.entity;

public class subscriped_user {

   String name;
   String meal_selected;
   String start_date;
   String end_date;
   String selectedMenu;
   int isPaused=0;
   String selectedSubscription;

   int userId;

   public subscriped_user(String name, String description, String selectedSubscription, String startDate, String endDate, String selectedMenu, int isPause, int userId) {

      this.name=name;
      this.meal_selected=description;
      this.selectedSubscription=selectedSubscription;
       this.start_date=startDate;
      this.end_date=endDate;
   this.selectedMenu=selectedMenu;
      this.isPaused=isPaused;
      this.userId=userId;

   }


   //   public subscriped_user(String name1, String description, int duration1, Date startDate, Date endDate, String selectedMenu, boolean isPaused, int userId) {
//      this.name=name1;
//      this.description=description;
//      this.selectedSubscription=duration1;
//       this.startdate=startDate;
//      this.lastdate=endDate;
//      this.selectedMenu=selectedMenu;
//      this.isPaused=isPaused;
//      this.userId=userId;
//   }


   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getDescription() {
      return meal_selected;
   }

   public void setDescription(String description) {
      this.meal_selected = description;
   }

   public String getStartdate() {
      return start_date;
   }

   public void setStartdate(String startdate) {
      this.start_date = startdate;
   }

   public String getLastdate() {
      return end_date;
   }

   public void setLastdate(String lastdate) {
      this.end_date = lastdate;
   }

   public String getSelectedMenu() {
      return selectedMenu;
   }

   public void setSelectedMenu(String selectedMenu) {
      this.selectedMenu = selectedMenu;
   }

   public int getIsPaused() {
      return isPaused;
   }

   public void setIsPaused(int isPaused) {
      this.isPaused = isPaused;
   }

   public String getSelectedSubscription() {
      return selectedSubscription;
   }

   public void setSelectedSubscription(String selectedSubscription) {
      this.selectedSubscription = selectedSubscription;
   }

   public int getUserId() {
      return userId;
   }

   public void setUserId(int userId) {
      this.userId = userId;
   }

   @Override
   public String toString() {
      return "subscriped_user{" +
              "name='" + name + '\'' +
              ", description='" + meal_selected + '\'' +
              ", startdate=" + start_date +
              ", lastdate=" + end_date +
              ", selectedmenu=" + selectedMenu +
              ", isPaused=" + isPaused +
              ", selectedSubscription=" + selectedSubscription +
              '}';
   }
}
