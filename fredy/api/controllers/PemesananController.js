/**
 * PemesananController
 *
 * @description :: Server-side logic for managing pemesanans
 * @help        :: See http://sailsjs.org/#!/documentation/concepts/Controllers
 */

module.exports = {
	index: function(req,res){
		Pemesanan.find().then(function (_pemesanan) {
		return res.json(_pemesanan);
		});
     },
     
     buatpesanan: function(req,res){
        return Pemesanan.create(req).then(function (_pemesanan) {
            //Pemesanan.find({select: ['id']}).then(function (_pemesanan){
                res.json("sukses")
            //});
            
         }).catch(function (err) {
             res.json('error input menu')
         });
     }


};

