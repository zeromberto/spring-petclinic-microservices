"use strict";angular.module("petForm").controller("PetFormController",["$http","$state","$stateParams",function(t,e,r){var n=this,a=r.ownerId||0;t.get("api/customer/petTypes").then(function(t){n.types=t.data}).then(function(){var e=r.petId||0;e?t.get("api/customer/owners/"+a+"/pets/"+e).then(function(t){n.pet=t.data,n.pet.birthDate=new Date(n.pet.birthDate),n.petTypeId=""+n.pet.type.id}):t.get("api/customer/owners/"+a).then(function(t){n.pet={owner:t.data.firstName+" "+t.data.lastName},n.petTypeId="1"})}),n.submit=function(){var r,o=n.pet.id||0,p={id:o,name:n.pet.name,birthDate:n.pet.birthDate,typeId:n.petTypeId};r=o?t.put("api/customer/owners/"+a+"/pets/"+o,p):t.post("api/customer/owners/"+a+"/pets",p),r.then(function(){e.go("owners",{ownerId:a})},function(t){var e=t.data;e.errors=e.errors||[],alert(e.error+"\r\n"+e.errors.map(function(t){return t.field+": "+t.defaultMessage}).join("\r\n"))})}}]);