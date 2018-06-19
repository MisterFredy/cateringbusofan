/**
 * BuktibayarpesananController
 *
 * @description :: Server-side logic for managing buktibayarpesanans
 * @help        :: See http://sailsjs.org/#!/documentation/concepts/Controllers
 */

module.exports = {
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
                       nominal:req.param("nominal")
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

