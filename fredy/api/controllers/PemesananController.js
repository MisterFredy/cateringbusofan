/**
 * PemesananController
 *
 * @description :: Server-side logic for managing pemesanans
 * @help        :: See http://sailsjs.org/#!/documentation/concepts/Controllers
 */

module.exports = {
	/* TAMBAHAN DARI AKHMAD */
	 /* Delete Pemesanan */
	 deletepemesanan: function(req,res){
		return pemesanan.update({id: req.param("id")}, {
            status: "0"
        }).then(function (_pemesanan) {
           res.json('suksesupdate');
        }).catch(function (err) {
            console.error("Error on ContactService.updateUser");
            console.error(err);
            return pemesanan.find().where({id: req.param("id")}).then(function (_pemesanan) {
                if (_pemesanan && _pemesanan.length > 0) {
                    return res.json('tidak menemukan id');
				}
            })
		});
     },


	 joinpemesanan: function(req,res){
        pemesanan.native(function(err, collection) {
            if (err) return res.serverError(err);
          
            collection.aggregate({
                $lookup:{
                    from: "user",
                    localField:"user",
                    foreignField:"user",
                    as: "user_docs"
                }
            }, {
            }).toArray(function (err, results) {
              if (err) return res.serverError(err);
              return res.ok(results);
            });
          });
    },


     /* TAMBAHAN DARI AKHMAD */
	 /* Update DP Value */
     updatedp: function(req,res){
		return pemesanan.update({id: req.param("id")}, {
            dp: parseInt(req.param("dp"))
        }).then(function (_pemesanan) {
           res.json('suksesupdate');
        }).catch(function (err) {
            console.error("Error on ContactService.updateUser");
            console.error(err);
            return pemesanan.find().where({id: req.param("id")}).then(function (_pemesanan) {
                if (_pemesanan && _pemesanan.length > 0) {
                    return res.json('tidak menemukan id');
				}
            })
		});
     },

     approvalbelum: function(req,res){
		return pemesanan.update({id: req.param("id")}, {
            pengaprove: req.param("pengaprove"),
            approval: "Belum Disetujui",
            alasan: "Belum Bayar DP"
        }).then(function (_pemesanan) {
           res.json('suksesupdate');
        }).catch(function (err) {
            console.error("Error on ContactService.updateUser");
            console.error(err);
            return pemesanan.find().where({id: req.param("id")}).then(function (_pemesanan) {
                if (_pemesanan && _pemesanan.length > 0) {
                    return res.json('tidak menemukan id');
				}
            })
		});
     },

     approvalsudah: function(req,res){
		return pemesanan.update({id: req.param("id")}, {
            pengaprove: req.param("pengaprove"),
            approval: "Sudah Disetujui",
            alasan: "Sudah Bayar DP"
        }).then(function (_pemesanan) {
           res.json('suksesupdate');
        }).catch(function (err) {
            console.error("Error on ContactService.updateUser");
            console.error(err);
            return pemesanan.find().where({id: req.param("id")}).then(function (_pemesanan) {
                if (_pemesanan && _pemesanan.length > 0) {
                    return res.json('tidak menemukan id');
				}
            })
		});
     },

     approvallunas: function(req,res){
		return pemesanan.update({id: req.param("id")}, {
            pengaprove: req.param("pengaprove"),
            approval: "Sudah Disetujui",
            alasan: "Lunas"
        }).then(function (_pemesanan) {
           res.json('suksesupdate');
        }).catch(function (err) {
            console.error("Error on ContactService.updateUser");
            console.error(err);
            return pemesanan.find().where({id: req.param("id")}).then(function (_pemesanan) {
                if (_pemesanan && _pemesanan.length > 0) {
                    return res.json('tidak menemukan id');
				}
            })
		});
     }
     



};

