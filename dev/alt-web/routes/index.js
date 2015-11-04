var express = require('express');
var router = express.Router();

/* Function to check if the user is authenticated */
var isAuthenticated = function (req, res, next) {
    // if user is authenticated in the session, call the next() to call the next request handler
    // Passport adds this method to request object. A middleware is allowed to add properties to
    // request and response objects
    if (req.isAuthenticated())
        return next();
    // if the user is not authenticated then redirect him to the login page
    res.redirect('/');
};

/* Function to check if the current authenticated as admin
   This also takes care of the isAuthenticated check */
var requireAdmin = function() {
    /* This function could be generalized for multiple roles
       if necessary by passing a role as a parameter */
    return function(req, res, next) {
        if (req.isAuthenticated() &&
                'user' in req.session &&
                req.session.user.admin === true)
            next();
        else
            res.send(403);
    };
};

module.exports = function(passport) {

    /* GET login page. */
    router.get('/', function(req, res) {
        // Display the Login page with any flash message, if any
        res.render('index', { message: req.flash('message') });
    });

    /* Handle Login POST */
    router.post('/login', passport.authenticate('login', {
        successRedirect: '/home',
        failureRedirect: '/',
        failureFlash : true
    }));

    /* GET Registration Page */
    router.get('/signup', requireAdmin, function(req, res) {
        res.render('register',{message: req.flash('message')});
    });

    /* Handle Registration POST */
    router.post('/signup', passport.authenticate('signup', {
        successRedirect: '/home',
        failureRedirect: '/signup',
        failureFlash : true
    }));

    /* GET Admin Page */
    router.get('/admin', requireAdmin, function(req, res) {
        res.render('admin', { user: req.user });
    });

    /* GET Home Page */
    router.get('/home', isAuthenticated, function(req, res) {
        res.render('home', { user: req.user });
    });

    /* Handle Logout */
    router.get('/signout', function(req, res) {
        req.logout();
        res.redirect('/');
    });

    return router;
}
