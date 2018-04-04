/**
 * UserController
 *
 * @description :: Server-side logic for managing users
 * @help        :: See http://sailsjs.org/#!/documentation/concepts/Controllers
 */

module.exports = {
	 index: function(req,res){
		user.find().then(function (_user) {
		return res.json(_user);
		});
	 },

	 loginapi: function(req,res){
		var _newContact = {
			username: req.param("username"),
			password: req.param("password")
		};
		user.find().where()
	 }
};

