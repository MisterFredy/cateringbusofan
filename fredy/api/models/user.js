/**
 * User.js
 *
 * @description :: TODO: You might write a short summary of how this model works and what it represents here.
 * @docs        :: http://sailsjs.org/documentation/concepts/models-and-orm/models
 */

module.exports = {

  attributes: {
    "id": {type:'objectid',primaryKey:true},
    "user": {type:'string'},
    "alamat": {type:'string'},
    "urlfoto": {type:'string'},
    "jenis_kelamin": {type:'string'},
    "no_hp": {type:'string'},
    "password": {type:'string'},
    "role":{type:'string'},
    "status":{type:'int'},
    "qrcode":{type:'string'}        
  }
};

