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
    req.flash('message','Must be logged in to see this page');
    res.redirect('/');
};

module.exports = function(passport) {

    /* GET login page. */
    router.get('/', function(req, res) {
        // If user is authenticated, redirect to appropriate home
        if (req.isAuthenticated()) {
            res.redirect(req.user.admin === true ? '/admin' : '/home');
        }
        // Display the Login page with any flash message, if any
        res.render('index', { message: req.flash('message') });
    });

    /* Handle Login POST */
    router.post('/login',
        passport.authenticate('login', { failureRedirect: '/', failureFlash : true }),
        function(req, res, next) {
            return res.redirect(req.user.admin === true ? '/admin' : '/home');
        }
    );

    /* GET Registration Page */
    router.get('/signup', function(req, res) {
        if (req.isAuthenticated() &&
                'user' in req && req.user.admin === true) {
            res.render('register',{
                message: req.flash('message'),
                user: req.user
            });
        } else {
            res.redirect('/home'); //TODO ideally, replace with 403
        }
    });

    /* Handle Registration POST */
    router.post('/signup', passport.authenticate('signup', {
        successRedirect: '/admin',
        failureRedirect: '/signup',
        failureFlash : true
    }));

    /* GET Admin Page */
    router.get('/admin', function(req, res) {
        if (req.isAuthenticated() &&
                'user' in req && req.user.admin) {
            res.render('admin', { user: req.user });
        } else {
            res.redirect('/home'); //TODO ideally, replace with 403
        }
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
