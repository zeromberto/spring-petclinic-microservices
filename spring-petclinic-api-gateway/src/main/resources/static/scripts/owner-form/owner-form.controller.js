"use strict";angular.module("ownerForm").controller("OwnerFormController",["$http","$state","$stateParams",function(r,n,e){var o=this,t=e.ownerId||0;t?r.get("api/customer/owners/"+t).then(function(r){o.owner=r.data}):o.owner={},o.submitOwnerForm=function(){var e,t=o.owner.id;e=t?r.put("api/customer/owners/"+t,o.owner):r.post("api/customer/owners",o.owner),e.then(function(){n.go("owners")},function(r){var n=r.data;alert(n.error+"\r\n"+n.errors.map(function(r){return r.field+": "+r.defaultMessage}).join("\r\n"))})}}]);