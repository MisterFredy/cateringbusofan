/**
 * BuktibayarpesananController
 *
 * @description :: Server-side logic for managing buktibayarpesanans
 * @help        :: See http://sailsjs.org/#!/documentation/concepts/Controllers
 */

module.exports = {
  /* TAMBAHAN DARI AKHMAD */
	 /* Delete Bukti */
	 deletebukti: function(req,res){
		return buktibayarpesanan.update({id: req.param("id")}, {
            status: "0"
        }).then(function (_buktibayarpesanan) {
           res.json('suksesupdate');
        }).catch(function (err) {
            console.error("Error on ContactService.updateUser");
            console.error(err);
            return buktibayarpesanan.find().where({id: req.param("id")}).then(function (_buktibayarpesanan) {
                if (_buktibayarpesanan && _buktibayarpesanan.length > 0) {
                    return res.json('tidak menemukan id');
				        }
            })
		    });
    },



     /* Update Konfirmasi Bukti */
	 bukticocok: function(req,res){
		return buktibayarpesanan.update({id: req.param("id")}, {
            konfirmasi: "Gambar Cocok Dengan Nominal",
            perespon: req.param("perespon")
        }).then(function (_buktibayarpesanan) {
           res.json('suksesupdate');
        }).catch(function (err) {
            console.error("Error on ContactService.updateUser");
            console.error(err);
            return buktibayarpesanan.find().where({id: req.param("id")}).then(function (_buktibayarpesanan) {
                if (_buktibayarpesanan && _buktibayarpesanan.length > 0) {
                    return res.json('tidak menemukan id');
				        }
            })
		    });
    },

    buktitidakcocok: function(req,res){
		return buktibayarpesanan.update({id: req.param("id")}, {
            konfirmasi: "Gambar Tidak Cocok Dengan Nominal",
            perespon: req.param("perespon")
        }).then(function (_buktibayarpesanan) {
           res.json('suksesupdate');
        }).catch(function (err) {
            console.error("Error on ContactService.updateUser");
            console.error(err);
            return buktibayarpesanan.find().where({id: req.param("id")}).then(function (_buktibayarpesanan) {
                if (_buktibayarpesanan && _buktibayarpesanan.length > 0) {
                    return res.json('tidak menemukan id');
				        }
            })
		    });
    },

	uploadbukti: function(req,res){
        var uploadfiles = req.file("bukti");
        uploadfiles.upload({
           dirname: '../../public/bukti', //directory folder upload
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
                   return buktibayarpesanan.create({
                       id_pemesanan:req.param("idpemesanan"),
                       url_img:filename,
                       nominal:parseInt(req.param("nominal")),
                       tanggal:req.param("tanggal"),
                       perespon:"",
                       konfirmasi:"Belum Dicek",
                       status:"1"
                    }).then(function (_buktibayarpesanan) {
                      res.json('suksesupdate');
                   }).catch(function (err) {
                       console.error("Error on ContactService.updateUser");
                       return json(err);
                   });  // True for success
                }
              });
    }
};

