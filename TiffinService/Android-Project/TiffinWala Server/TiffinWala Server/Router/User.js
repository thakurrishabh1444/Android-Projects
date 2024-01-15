const express = require("express")
const db = require("../db")
const utils = require("../Utils")

const router = express.Router()

router.post("/register",(request,response)=>
{
    console.log("request coming");
    const{name,email,password,phone,address,area} = request.body
    console.log(name , password, phone, address, area);
    db.query("insert into Users(name,email,password,phone,address,area) VALUES (?,?,?,?,?,?)",
    [name,email,password,phone,address,area],(error,result)=>
    {
        response.send(utils.createResult(error, result))  
    })
})

router.post("/login",(request,response)=>
{
    console.log("request coming");
    const{email,password}=request.body
    db.query("select * from Users where email=? AND password=?",[email,password],(error,result)=>
    {
        response.send(utils.createResult(error, result))
     
    })
})

router.post("/update",(request,response)=>
{
    const{name,email,password,phone,address,area,userId}=request.body
    console.log(name,email,password,phone,address,area,userId);

    console.log("request coming");
    db.query("UPDATE Users set name=?,email=?,password=?,phone=?,address=?,area=? where userId=?",
    [name, email, password, phone, address, area, userId],(error,result)=>
    {
        response.send(utils.createResult(error, result))
     
    })
})

router.get("/all/:userId",(request,response)=>
{
    const{userId}=request.params
      console.log("coming");
      db.query("SELECT * from Users where userId=?",[userId],(error,result)=>{
        response.send(utils.createResult(error, result))
      });
})


router.get("/details/:userId",(request,response)=>
{
    const{userId}=request.params
    console.log("coming");
      db.query("SELECT Users.name,subscriptions.start_date,subscriptions.end_date,orders.totalAmount FROM Users JOIN subscriptions ON Users.userId = subscriptions.userId JOIN orders ON subscriptions.userId = orders.userId WHERE Users.userId = ?",[userId],(error,result)=>{
        response.send(utils.createResult(error, result))
      });
})



// router.post("/update/:userId",(request,response)=>
// {
//       const{}=request.body;
//       const{userId}=request.params
//       console.log("coming");
//       db.query("UPDATE User SET ",[userId],(error,result)=>{
//         response.send(utils.createResult(error, result))
//       });
// })


// router.get("/:userId",(request,response)=>
// {
//     const{userId}=request.params
//       db.query("SELECT * from Users where userId=?",[userId],(error,result)=>{
//         response.send(utils.createResult(error, result))
//       });
// })

// router.get("/Fk/:subscriptionID",(request,response)=>
// {
//     const{subscriptionID}=request.params
//       db.query("select * from Users,Subscriptions where Users.subscriptionID=Subscriptions.subscriptionID and Users.subscriptionID=?",[subscriptionID],(error,result)=>{
//         response.send(utils.createResult(error, result))
//       });
// })

module.exports = router