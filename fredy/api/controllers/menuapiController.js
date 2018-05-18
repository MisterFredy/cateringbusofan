/**
 * MenuapiController
 *
 * @description :: Server-side logic for managing menus
 * @help        :: See http://sailsjs.org/#!/documentation/concepts/Controllers
 */

module.exports = {
	index: function(req,res){
		menu.find().then(function (_menu) {
		return res.json(_menu);
		});
	 },

	tambah: function(req,res){
		var _newmenu = {
			"category" : req.param("kategori"),
    "menu":[{
      "nama_menu": req.param("nama"),
      "harga_default": req.param("harga"),
      "deskripsi": req.param("deskripsi"),
      "url_img": req.param("urlfoto"),
      "detail_menu": [
        req.param("menu")
      ]
	}],
	"status":1
		 }

		 return menu.create(_newmenu).then(function (_menu) {
           res.json('sukses')
        }).catch(function (err) {
			res.json('error input menu')
		});
	},

	carikategori: function(req,res){
        var kategori = req.param("kategori");
        var splitkategori = kategori.split("_").join(" ");
		menu.find().where({category:splitkategori}).then(function(_menu){
		return res.json(_menu);
		});
	},

    cariaktifmenu: function(req,res){
        menu.find(menu).where({
            "menu.status":1
        }).then(function(_menu){
            return res.json(_menu);
        });
    },

    updatemenuapi: function(req,res){
		return menu.update({_id: req.param("id")}, {
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
            return menu.find().where({_id: req.param("_id")}).then(function (_menu) {
                if (_menu && _menu.length > 0) {
                    return res.json('tidak menemukan _id');
				}
            })
		});
	 },

	 deletemenuapi: function(req,res){
		return menu.update({_id: req.param("id")}, {
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

            return menu.find().where({_id: req.param("_id")}).then(function (_menu) {
                if (_menu && _menu.length > 0) {
                    return res.json('tidak menemukan _id');
				}
            })
		});
	 },



	
};

