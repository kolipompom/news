# news
SIGN UP

POST /authors 
Host: localhost:8681
Content-Type: application/json
REQUEST
{
    "firstName":"Temi",
    "lastName":"Lade",
    "email":"temilade@gmail.com",
    "password":"Password@123",
    "address":" Baruwa,Ipaja,Lagos State",
    "phone":"08033456510"
}

RESPONSE

{
    "status": "SUCCESS",
    "data": {
        "author": {
            "uniqueKey": "3b5fc4b338219596d822820ad23d0660",
            "firstName": "Temi",
            "lastName": "Lade",
            "email": "temilade@gmail.com",
            "address": " Baruwa,Ipaja,Lagos State",
            "roleId": "9fa738f491aefd5636ee76ff8971303e",
            "phone": "08033456510",
            "status": "ACTIVE",
            "lastLoginDate": null,
            "roleName": null,
            "createdAt": "2020-01-28T22:11:17.701",
            "updatedAt": null
        }
    }
}

LOG IN

POST /authors/authenticate 
Host: localhost:8681
Content-Type: application/json
REQUEST
{
    "email" : "akintoyekolawole@gmail.com",
    "password" : "Password@123"
}

RESPONSE

{
    "status": "SUCCESS",
    "data": {
        "auth": {
            "token": "bace3dcefe79044a44fee6feb2020d78",
            "expires": "2020-01-28T21:53:10.394",
            "tokenType": "bearer"
        },
        "author": {
            "uniqueKey": "6054fd09b51e3ab8049f7ec51c2e296c",
            "firstName": "Kolawole",
            "lastName": "Akintoye",
            "email": "akintoyekolawole@gmail.com",
            "address": "No 12 Dada Close, Baruwa,Ipaja,Lagos State",
            "roleId": "9fa738f491aefd5636ee76ff8971303e",
            "phone": "08033456500",
            "status": "ACTIVE",
            "lastLoginDate": "2020-01-28T21:48:10.389",
            "roleName": null,
            "createdAt": "2020-01-28T21:09:19.03",
            "updatedAt": "2020-01-28T21:48:10.396"
        },
        "permission": [
            "submit_news",
            "edit_news",
            "delete_news"
        ]
    }
}


PUBLISH NEWS

This request in a form-data format.

POST /publications 
Host: localhost:8681
S-Author-Token: e47be787e794eca5331a3fcbb3304377
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW

----WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="file"; filename="/C:/Users/Akintoye Kolawole/Desktop/IMG_20190903_123746.jpg"
Content-Type: image/jpeg

(data)
----WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="description"

The project page 2
----WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="status"

ACTIVE
----WebKitFormBoundary7MA4YWxkTrZu0gW

RESPONSE

{
    "status": null,
    "data": {
        "news": {
            "news": "files\\6054fd09b51e3ab8049f7ec51c2e296c-IMG_20190903_123746.jpg",
            "description": "The project page 2",
            "status": "ACTIVE",
            "createdAt": "2020-01-28T22:16:47.902",
            "updatedAt": null
        }
    }
}

EDIT PUBLISHED NEWS

PUT /publications/5094fef81cc2c683f8b0f483e7752f4e 
Host: localhost:8681
S-Author-Token: 747d9dfd89cb07a11360fc7613cf81b8
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW

----WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="file"; filename="/C:/Users/Akintoye Kolawole/Desktop/IMG_20190903_123823.jpg"
Content-Type: image/jpeg

(data)
----WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="description"

Update news test
----WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="status"

INACTIVE
----WebKitFormBoundary7MA4YWxkTrZu0gW

RESPONSE

{
    "status": null,
    "data": {
        "news": {
            "news": "files\\6054fd09b51e3ab8049f7ec51c2e296c-IMG_20190903_123823.jpg",
            "description": "Update news test",
            "status": "INACTIVE",
            "createdAt": "2020-01-28T21:48:20.425",
            "updatedAt": null
        }
    }
}

DELETE PUBLISHED NEWS

DELETE /publications/bdc3823dda936f964a3f5c0b57fcd93e 
Host: localhost:8681
S-Author-Token: 949d8dc231a62a01a9490ae6f81ceed2


RESPONSE

{
    "status": null,
    "data": {
        "news": [
            {
                "news": "files\\6054fd09b51e3ab8049f7ec51c2e296c-IMG_20190903_123746.jpg",
                "description": "The project page 2",
                "status": "ACTIVE",
                "createdAt": "2020-01-28T21:48:59.201",
                "updatedAt": null
            },
            {
                "news": "files\\6054fd09b51e3ab8049f7ec51c2e296c-IMG_20190903_123823.jpg",
                "description": "Update news test",
                "status": "INACTIVE",
                "createdAt": "2020-01-28T21:48:20.425",
                "updatedAt": "2020-01-28T22:22:09.168"
            }
        ]
    }
}


LOG OUT

POST /authors/logout 
Host: localhost:8681
S-User-Token: f5f3137e10d36ac38e760f7b5a9c1acb

RESPONSE

{
    "status": "SUCCESS",
    "data": {
        "author": {
            "uniqueKey": "6054fd09b51e3ab8049f7ec51c2e296c",
            "firstName": "Kolawole",
            "lastName": "Akintoye",
            "email": "akintoyekolawole@gmail.com",
            "address": "No 12 Dada Close, Baruwa,Ipaja,Lagos State",
            "roleId": "9fa738f491aefd5636ee76ff8971303e",
            "phone": "08033456500",
            "status": "ACTIVE",
            "lastLoginDate": "2020-01-28T22:26:01.512",
            "roleName": null,
            "createdAt": "2020-01-28T21:09:19.03",
            "updatedAt": "2020-01-28T22:25:26.962"
        }
    }
}


VIEW ALL AUTHORS

GET /authors
Host: localhost:8681

RESPONSE

{
    "status": "SUCCESS",
    "data": {
        "author": [
            {
                "uniqueKey": "3b5fc4b338219596d822820ad23d0660",
                "firstName": "Temi",
                "lastName": "Lade",
                "email": "temilade@gmail.com",
                "address": " Baruwa,Ipaja,Lagos State",
                "roleId": "9fa738f491aefd5636ee76ff8971303e",
                "phone": "08033456510",
                "status": "ACTIVE",
                "lastLoginDate": null,
                "roleName": null,
                "createdAt": "2020-01-28T22:11:17.701",
                "updatedAt": null
            },
            {
                "uniqueKey": "319588912aeef52d67c7ed3cd500cff1",
                "firstName": "Dare",
                "lastName": "Yemi",
                "email": "dareyemi@gmail.com",
                "address": " Baruwa,Ipaja,Lagos State",
                "roleId": "9fa738f491aefd5636ee76ff8971303e",
                "phone": "080334256510",
                "status": "ACTIVE",
                "lastLoginDate": null,
                "roleName": null,
                "createdAt": "2020-01-28T22:15:03.262",
                "updatedAt": null
            },
            {
                "uniqueKey": "6054fd09b51e3ab8049f7ec51c2e296c",
                "firstName": "Kolawole",
                "lastName": "Akintoye",
                "email": "akintoyekolawole@gmail.com",
                "address": "No 12 Dada Close, Baruwa,Ipaja,Lagos State",
                "roleId": "9fa738f491aefd5636ee76ff8971303e",
                "phone": "08033456500",
                "status": "ACTIVE",
                "lastLoginDate": "2020-01-28T22:25:26.953",
                "roleName": null,
                "createdAt": "2020-01-28T21:09:19.03",
                "updatedAt": "2020-01-28T22:25:26.962"
            }
        ]
    }
}

VIEW ALL PUBLICATIONS

GET /publications
Host: localhost:8681

RESPONSE

{
    "status": "SUCCESS",
    "data": {
        "author": [
            {
                "news": "files\\6054fd09b51e3ab8049f7ec51c2e296c-IMG_20190903_123746.jpg",
                "description": "The project page 2",
                "status": "ACTIVE",
                "createdAt": "2020-01-28T21:48:59.201",
                "updatedAt": null
            },
            {
                "news": "files\\6054fd09b51e3ab8049f7ec51c2e296c-IMG_20190903_123823.jpg",
                "description": "Update news test",
                "status": "INACTIVE",
                "createdAt": "2020-01-28T21:48:20.425",
                "updatedAt": "2020-01-28T22:22:09.168"
            }
        ]
    }
}
