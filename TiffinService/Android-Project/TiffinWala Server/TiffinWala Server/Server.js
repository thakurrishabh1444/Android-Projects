const express = require("express")
const cors = require("cors")
const routerUser = require("./Router/User")

const routerSubscription = require("./Router/subscriptions")
const routerOrder = require("./Router/order")

const app = express()
app.use(express.json())
app.use(cors("*"))
app.use("/User",routerUser)
app.use("/sububscription",routerSubscription)
app.use("/Order",routerOrder)

app.listen(4000, "192.168.19.16", () => {
    console.log("Server started at port 4000")
  })