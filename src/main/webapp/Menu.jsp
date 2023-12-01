<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hospital Menu</title>
    <link rel="stylesheet" type="text/css" href="MenuStyle.css">
</head>
<body>

<header>
    <h1>Welcome, Dr. Smith!</h1>
</header>

<nav>
    <div class="dropdown">
        <a href="#">Dashboard</a>
        <div class="dropdown-content">
            <a href="#">Option 1</a>
            <a href="#">Option 2</a>
            <!-- Add more dropdown options as needed -->
        </div>
    </div>
    
    <div class="dropdown">
        <a href="#">Appointments</a>
        <div class="dropdown-content">
            <a href="#">View Appointments</a>
            <a href="#">Cancel Appointments</a>
            <a href="#">Set Availability</a>
            <a href="#">Update Availability</a>
            <a href="#">View Previous Appointments</a>
            <!-- Add more dropdown options as needed -->
        </div>
    </div>
    
    <div class="dropdown">
       <a href="#">Patients</a>
       <div class="dropdown-content">
            <a href="#">View Patients</a>
            <a href="SmartSearch.jsf">Patient Medical History</a>
            <!-- Add more dropdown options as needed -->
        </div>
    </div>
    
    <div class="dropdown">
        <a href="#">Prescriptions</a>
        <div class="dropdown-content">
            <a href="#">View Prescription</a>
            <!-- <a href="#">Option 2</a> -->
            <!-- Add more dropdown options as needed -->
        </div>
    </div>
    <!-- <div class="dropdown">
        <a href="#">Reports</a>
        <div class="dropdown-content">
            <a href="#">Option 1</a>
            <a href="#">Option 2</a>
            Add more dropdown options as needed
        </div>
    </div> -->
     <div class="dropdown">
        <a href="#">Logout</a>
        <div class="dropdown-content">
            <a href="#">Logout</a>
            <a href="#">Change Password</a>
            <!-- Add more dropdown options as needed -->
        </div>
    </div>
</nav>

<section>
    <h2>Dashboard</h2>
    <p>This is your personalized dashboard, Dr. Smith. It provides a quick overview of your daily activities, upcoming appointments, and important notifications. Stay updated and manage your tasks efficiently.</p>
    <!-- Add your dashboard content here -->
</section>

<footer>
    &copy; 2023 Your Hospital
</footer>

</body>
</html>
