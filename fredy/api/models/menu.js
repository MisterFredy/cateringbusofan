/**
 * Menu.js
 *
 * @description :: TODO: You might write a short summary of how this model works and what it represents here.
 * @docs        :: http://sailsjs.org/documentation/concepts/models-and-orm/models
 */

module.exports = {

  attributes: {
    "id": {type:'objectid',primaryKey:true},
    "category" : {type:'string'},
    "menu":[{
      "nama_menu": {type:'string'},
      "harga_default": {type:'number'},
      "deskripsi": {type:'string'},
      "url_img": {type:'string'},
      "detail_menu": [
        {type:'string'}
      ],
      "status": {type:'number'}
    }]
  }
  
};

