/**
 * MenuapiController
 *
 * @description :: Server-side logic for managing menus
 * @help        :: See http://sailsjs.org/#!/documentation/concepts/Controllers
 */

module.exports = {
	index: function(req,res){
		menu.find().where({status:1}).then(function (_menu) {
		return res.json(_menu);
		});
	 },

	tambah: function(req,res){
		var _newmenu = {
			"nama": req.param("nama"),
			"deskripsi": req.param("deskripsi"),
			"harga": req.param("harga"),
			"kategori": req.param("kategori"),
			"urlfoto": req.param("urlfoto"),
			"status": req.param("1")
		 }

		 return Contact.create(_newmenu).then(function (_menu) {
           res.json('sukses')
        }).catch(function (err) {
			res.json('error input menu')
		});
	},

	carikategori: function(req,res){
		menu.find().where({kategori: req.param("kategori")}).then(function(_menu){
		return res.json(_menu);
		});
	},

	updatemenuapi: function(req,res){
		return menu.update({pid: req.param("pid")}, {
            nama: req.param("nama"),
            deskripsi: req.param("deskripsi"),
            harga: req.param("harga"),
            kategori: req.param("kategori"),
            urlfoto: req.param("urlfoto"),
            status: req.param("status")
        }).then(function (_menu) {
           res.json('suksesupdate');
        }).catch(function (err) {
            console.error("Error on ContactService.updatemenu");
            console.error(err);
            return menu.find().where({pid: req.param("pid")}).then(function (_menu) {
                if (_menu && _menu.length > 0) {
                    return res.json('tidak menemukan pid');
				}
            })
		});
	 },

	 deletemenuapi: function(req,res){
		return menu.update({nama: req.param("nama")}, {
            nama: req.param("nama"),
            deskripsi: req.param("deskripsi"),
            harga: req.param("harga"),
            kategori: req.param("kategori"),
            urlfoto: req.param("urlfoto"),
			status: 0
        }).then(function (_menu) {
           res.json('suksesupdate');
        }).catch(function (err) {
            console.error("Error on ContactService.updatemenu");
            console.error(err);

            return menu.find().where({pid: req.param("pid")}).then(function (_menu) {
                if (_menu && _menu.length > 0) {
                    return res.json('tidak menemukan pid');
				}
            })
		});
	 },



	
};

