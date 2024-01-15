const express = require("express")
const db = require("../db")
const utils = require("../Utils");

const router = express.Router()

//insert new order of user

router.post("/insert_order", (request, response) => {
  console.log("insert order");
  const{totalAmount,subscriptionId,userId} = request.body
  db.query("insert into orders(totalAmount,subscriptionId,userId) VALUES (?,?,?)",
  [totalAmount,subscriptionId,userId],(error,result)=>
  {
      response.send(utils.createResult(error, result))  
  }) 
})


router.get("/getall", (request, response) => {
    const id = request.params.id
    db.query("SELECT * from orders",(error, result) => {
      response.send(utils.createResult(error, result))
    })
  })

  router.post("/:orderId", (request, response) => {
    const orderId = request.params.orderId
    db.query("SELECT * from orders where orderId=?",[orderId],(error, result) => {
      response.send(utils.createResult(error, result))
    })
  })

  

  router.delete("/:orderId", (request, response) => {
    const orderId = request.params.orderId
    
    db.query("delete  from orders where orderId=?",[orderId],(error, result) => {
      response.send(utils.createResult(error, result))
    })
  })



  // router.get("/:cat", (request, response) => {

  //   const {cat}= request.params

  //   var menu = ""

  //   if(cat === "veg")
  //   {
  //      menu = "Menu A"
  //   }
  //   else if(cat === "nonveg"){

  //     menu = "Menu B"
  //   }

  //   console.log(menu)
  //   db.query("select subscriptions.selectedMenu from orders,subscriptions where orders.subscriptionId=subscriptions.subscriptionId AND subscriptions.selectedMenu= ?",[menu],(error, result) => {
  //     response.send(utils.createResult(error, result))
  //   })
  // })

  router.get("/:cat", (request, response) => {

    const {cat}= request.params

    var menu = ""

    if(cat === "veg")
    {
       menu = "Menu A"
    }
    else if(cat === "nonveg"){

      menu = "Menu B"
    }

    console.log(menu)
    db.query("select count(subscriptions.selectedMenu) from orders,subscriptions where orders.subscriptionId=subscriptions.subscriptionId AND subscriptions.selectedMenu= ?",[menu],(error, result) => {
      response.send(utils.createResult(error, result))
    })
  })

  module.exports = router;