/**
 * MenuController
 *
 * @description :: Server-side logic for managing menus
 * @help        :: See http://sailsjs.org/#!/documentation/concepts/Controllers
 */

module.exports = {
	index: function(req,res){
		menu.find().then(function (_menu) {
		return res.json(_menu);
		});
	 }
};

