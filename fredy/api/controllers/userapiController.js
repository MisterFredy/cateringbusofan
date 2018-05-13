/**
 * UserapiController
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

	 tambahuser: function(req,res){
		 var _newuser = {
			"user": req.param("user"),
			"alamat": req.param("alamat"),
			"urlfoto": "null",
			"jenis_kelamin": req.param("jenis_kelamin"),
			"no_hp": req.param("no_hp"),
			"password": req.param("password"),
			"role":req.param("role"),
			"status":1,
			"qrcode" :"null" 
		 }

		 //return res.json(_newuser);
		 return user.create(_newuser).then(function (_user) {
			return res.json('sukses');
        }).catch(function (err) {
			console.error("Error on ContactService.createContact");
            console.error(err);
			console.error(JSON.stringify(err));
			return res.json('error input user');
		});
	 },

	 getuserdetail: function(req,res){
		user.find().where({user:req.param("username")}).then(function(_menu){
		return res.json(_menu);
		});
	 },

	 updateuserapi: function(req,res){
		return user.update({pid: req.param("pid")}, {
            user: req.param("username"),
            alamat: req.param("alamat"),
            urlfoto: req.param("urlfoto"),
            jenis_kelamin: req.param("jenis_kelamin"),
            no_hp: req.param("no_hp"),
            role: req.param("role")
        }).then(function (_user) {
           res.json('suksesupdate');
        }).catch(function (err) {
            console.error("Error on ContactService.updateUser");
            console.error(err);

            return user.find().where({pid: req.param("pid")}).then(function (_user) {
                if (_user && _user.length > 0) {
                    return res.json('tidak menemukan pid');
				}
            })
		});
	 },


	 deleteuserapi: function(req,res){
		return user.update({pid: req.param("pid")}, {
            username: req.param("username"),
            alamat: req.param("alamat"),
            urlfoto: req.param("urlfoto"),
            jenis_kelamin: req.param("jenis_kelamin"),
            no_hp: req.param("no_hp"),
			role: req.param("role"),
			status: 0
        }).then(function (_user) {
           res.json('suksesupdate');
        }).catch(function (err) {
            console.error("Error on ContactService.updateUser");
            console.error(err);

            return user.find().where({pid: req.param("pid")}).then(function (_user) {
                if (_user && _user.length > 0) {
                    return res.json('tidak menemukan pid');
				}
            })
		});
	 },


};

