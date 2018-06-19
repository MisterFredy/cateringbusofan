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
			"status":"1",
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
		user.find().where({user:req.param("username")}).then(function(_user){
		return res.json(_user);
		});
	 },

	/*getuserdetail: function(req,res){
		user.find({id:req.param("id")}).then(function(_user){
		return res.json(_user);
		});
	 },*/

	 updateuserapi: function(req,res){
		return user.update({id: req.param("id")}, {
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
            return user.find().where({id: req.param("id")}).then(function (_user) {
                if (_user && _user.length > 0) {
                    return res.json('tidak menemukan id');
				}
            })
		});
	 },


	 deleteuserapi: function(req,res){
		return user.update({id: req.param("id")}, {
            username: req.param("username"),
            alamat: req.param("alamat"),
            urlfoto: req.param("urlfoto"),
            jenis_kelamin: req.param("jenis_kelamin"),
            no_hp: req.param("no_hp"),
			role: req.param("role"),
			status: 0
        }).then(function (_user) {
           res.json('suksesdelete');
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

	 uploadfiles: function(req,res){
		 var uploadfiles = req.file("avatar");
		 uploadfiles.upload({
			dirname: '../../public/profile', //directory folder upload
			maxBytes: 3 * 1024 * 1024, //3 MB
			allowedTypes: ['image/jpeg', 'image/png']
		 }
		 , function onUploadComplete(err, files) {
			if (err) {
				   return res.json({
					 status: false,
					 msg: 'uploading error'
				   }); // False for err
				 } else {
					 var filename = files[0].fd.split('/').reverse()[0];
					return user.update({user: req.param("username")}, {
						urlfoto: filename
					}).then(function (_user) {
					   res.json('suksesupdate');
					}).catch(function (err) {
						console.error("Error on ContactService.updateUser");
						console.error(err);
						return user.find().where({user: req.param("username")}).then(function (_user) {
							if (_user && _user.length > 0) {
								return res.json('tidak menemukan username');
							}
						})
					});  // True for success
				 }
			   });
	 }


};

