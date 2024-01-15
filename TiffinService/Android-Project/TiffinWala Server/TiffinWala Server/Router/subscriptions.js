const express = require("express")
const db = require("../db")
const utils = require("../Utils");

const router = express.Router()

// router.post('/:userId', (req, res) => {
//     const userId = req.params.userId;
//     const { name, description, selectedSubscription, start_date, end_date, selectedMenu } = req.body;
  
   
//     const sql = `INSERT INTO subscriptions (name, description, selectedSubscription, start_date, end_date, selectedMenu, userId) VALUES (?, ?, ?, ?, ?, ?, ?)`;
  
    
//     db.query(sql, [name, description, selectedSubscription, start_date, end_date, selectedMenu, userId], (err, result) => {
//       if (err) {
//         console.error(err);
//         return res.status(500).json({ error: 'Failed to select subscription' });
//       }
  
//       res.status(200).json({ message: 'Subscription selected successfully' });
//     });
//   });

//   router.get('/:userId', (req, res) => { 
//     const userId = req.params.userId;
  
    
//     const sql = `SELECT s.name AS name, s.start_date, s.end_date, DATEDIFF(s.end_date, CURDATE()) AS days_remaining
//       FROM subscriptions AS s
//       WHERE s.userId = ?`;
  
    
//     db.query(sql, [userId], (err, result) => {
//       if (err) {
//         console.error(err);
//         return res.status(500).json({ error: 'Failed to fetch subscription details' });
//       }
  
//       if (result.length === 0) {
//         return res.status(404).json({ error: 'User not found or no subscription details found' });
//       }
  
//       const subscription = result[0];
//       const { name, start_date, end_date, days_remaining } = subscription;
  
//       res.status(200).json({
//         name,
//         start_date,
//         end_date,
//         days_remaining
//       });
//     });
//   });

//  // Endpoint to get the total subscriptions by month
// router.get('/', (req, res) => {
//   // Assuming you have a "subscriptions" table in your database
//   const sql = `
//     SELECT MONTH(start_date) AS month, COUNT(*) AS total_subscriptions
//     FROM subscriptions
//     GROUP BY MONTH(start_date)
//     ORDER BY MONTH(start_date)`;

//   // Execute the SQL query to fetch the total subscriptions by month
//   db.query(sql, (err, result) => {
//     if (err) {
//       console.error(err);
//       return res.status(500).json({ error: 'Failed to fetch total subscriptions by month' });
//     }

//     res.status(200).json(result);
//   });
// });

// router.post('/insert', (req, res) => {
  
//   const { name, description, selectedSubscription, start_date, end_date, selectedMenu,isPause,userId } = req.body;

 
//   const sql = `INSERT INTO subscriptions (name, description, selectedSubscription, start_date, end_date, selectedMenu,isPause, userId) VALUES (?, ?, ? , ?, ?, ?, ?, ?)`;

  
//   db.query(sql, [name, description, selectedSubscription, start_date, end_date, selectedMenu, userId], (err, result) => {
//     if (err) {
//       console.error(err);
//       return res.status(500).json({ error: 'Failed to select subscription' });
//     }

//     res.status(200).json({ message: 'Subscription selected successfully' });
//   });
// });

router.post("/insert_user",(request,response)=>
{
  console.log("insert ");
  const{name,meal_selected,selectedSubscription,start_date,end_date,selectedMenu,isPaused,userId}= request.body;
  db.query(
    "INSERT INTO subscriptions(name, meal_selected, selectedSubscription, start_date, end_date, selectedMenu, isPaused, userId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
    [name, meal_selected, selectedSubscription, start_date, end_date, selectedMenu, isPaused, userId],
    (error, result) => {
        response.send(utils.createResult(error, result)); 
    }
);
});


// router.post("/insert_date",(request,response)=>
// {
//   console.log("inside date");
//   const{selected_date}= request.body;
//   console.log(selected_date);
 
//   db.query("INSERT INTO temp(selected_date) VALUES (?)",[selected_date],(error, result) => {
//   console.log(error);
//         response.send(utils.createResult(error, result)); 
//     }
// );
// });


router.get("/checkAlreadySubscribeOrNot/:userId",(request,response)=>
{
     console.log("check subscribe or not");
    const{userId}=request.params
      db.query("SELECT subscriptionId from subscriptions where userId=?",[userId],(error,result)=>{
        response.send(utils.createResult(error, result))
      });
})


router.get("/:userId",(request,response)=>
{
     console.log("fetch sub id");
    const{userId}=request.params
      db.query("SELECT subscriptionId from subscriptions where userId=?",[userId],(error,result)=>{
        response.send(utils.createResult(error, result))
      });
})

  module.exports = router;