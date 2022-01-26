$(document).ready(
    function () {
        $.ajax({
            url: "/regions",
            type: "GET",
            success: function (regions) {
                // $('#region').empty();
                $.each(regions, function (i, region) {
                    $('#region').append($("<option/>", {
                        value: region.id,
                        text: region.name
                    }));
                });
            },
            error: function (jqXHR, exception) {
                console.log();
            }
        })
        $('#region').change(function () {
            var id = this.value;
            console.log(id)
            getDistrict(id)
        })
    })

function getDistrict(id) {
    $.ajax({
        url: "/districts",
        data: {regionId: id},
        type: "GET",
        success: function (districts) {
            $('#district').empty();
            $.each(districts, function (i, district) {
                $('#district').append($("<option/>", {
                    value: district.id,
                    text: district.name
                }));
            });
        },
        error: function (jqXHR, exception) {
            console.log(error);
        }
    })
}
$(document).ready(function () {

    // add address regions
    $("#address").click(function () {
        $.ajax({
            type:'GET',
            url:"/districts/" + parseInt($("#region").val())+ "/" +parseInt($("#district").val()),
            async:false,
            success:function (order){
                var msg = '';
                $('#checkAddress').html(msg);
            }
        })
    });

// get my books
    var bookId;
    $("#getMyBooks").click(function () {
        $.ajax({
            type:'GET',
            url:"/actionBook/myBooks",
            async:false,
            success:function (data){
                var div1 = document.getElementById('carouselid');
                var div2 = document.getElementById('myBooks');
                var div4 = document.getElementById('pstyle');
                var div5 = document.getElementById('pstyle2');
                // var div3 = document.getElementById('mySearchedBooks');
                div1.style.display = 'none';
                div2.style.display = 'contents';
                div4.style.removeProperty("display");
                div4.style.display = 'show';
                div5.style.display = 'none';

                var myBooks = $("#myBooks");
                $("#myBooks").empty();
                $("#mySearchedBooks").empty();
                $("#allBooks").empty();
                for (i = 0; i < data.length; i++) {
                    bookId = data[i].bookid;
                    myBooks.append("<div class='card'>" +
                        "<div class='img'>"+
                        '<img  src="data:image/jpg;base64,'+data[i].picture+'"/>'+"</div>"+
                        "<div class='content'>" +
                        "<p class='title' style='text-transform: uppercase'> " + data[i].name + "</p>"           +
                        // "<p class='title' style='text-transform: uppercase'> " + data[i].bookid + "</p>" +
                        // "<input type='button' class='card1-btn' value='O`zgartirish'>"+
                        "<input type='button'  class='card2-btn' data-id='"+data[i].bookid+"'  value='O`chirish' data-toggle='modal' data-target='#deleteBookModal "+bookId+"' >"+
                        "<input type='button'  class='card1-btn' data-id='"+data[i].bookid+"'  value='O`zgartirish' data-toggle='modal' data-target='#editBookModal "+bookId+"' >"+
                        "</div></div>" +
                        "<div class=\"modal fade\" id= \"editBookModal "+bookId+"\" role=\"dialog\" tabindex=\"-1\" aria-labelledby=\"mySmallModalLabel\" aria-hidden=\"true\">\n" +
                        "      <div class=\"modal-dialog\">\n" +
                        "\n" +
                        "        <!-- Add book content-->\n" +
                        "        <div class=\"modal-content\" id=\"modalContent\">\n" +
                        "          <div class=\"modal-header\">\n" +
                        "            <button type=\"button\" class=\"close\" data-dismiss=\"modal\">&times;</button>\n" +
                        "          </div>\n" +
                        "          <div class=\"modal-body\">\n" +
                        "            <form class=\"needs-validation\" novalidate>\n" +
                        "              <div class=\"form-row\">\n" +
                        "                <div class=\"col-md-4 mb-3 md-form\">\n" +
                        "                  <label for=\"name\">Kitob nomi:</label>\n" +
                        "                  <input type=\"text\" name=\"name\" class=\"form-control\" id=\"name\" placeholder=\"\" value='dsa' " +
                        "                         required>\n" +
                        "                  <div class=\"valid-feedback\">\n" +
                        "                    Looks good!\n" +
                        "                  </div>\n" +
                        "                </div>\n" +
                        "                <div class=\"col-md-4 mb-3 md-form\">\n" +
                        "                  <label for=\"author\">Yozuvchi:</label>\n" +
                        "                  <input type=\"text\" name=\"author\" class=\"form-control\" id=\"author\" value=\"\" required>\n" +
                        "                  <div class=\"valid-feedback\">\n" +
                        "                    Looks good!\n" +
                        "                  </div>\n" +
                        "                </div>\n" +
                        "                <div class=\"col-md-4 mb-3 md-form\">\n" +
                        "                  <label for=\"language\">Tilni tanlang:</label>\n" +
                        "                  <select id=\"language\" name=\"language\" class=\"form-control\" aria-describedby=\"inputGroupPrepend2\" required>\n" +
                        "                    <option value=\"0\">O'zbek</option>d\n" +
                        "                    <option value=\"1\">Rus</option>\n" +
                        "                    <option value=\"2\">Kiril</option>\n" +
                        "                    <option value=\"3\">English</option>\n" +
                        "                    <option value=\"4\">Boshqa</option>\n" +
                        "                  </select>\n" +
                        "                  <div class=\"invalid-feedback\">\n" +
                        "                    <!--                                Please choose a username.-->\n" +
                        "                  </div>\n" +
                        "                </div>\n" +
                        "              </div>\n" +
                        "              <div class=\"form-row\">\n" +
                        "                <div class=\"col-md-6 mb-3 md-form\">\n" +
                        "                  <label for=\"comment\">Kitob haqida izoh:</label>\n" +
                        "                  <textarea name=\"comment\" type=\"text\" id=\"comment\"  class=\"form-control\" required></textarea>\n" +
                        "                  <div class=\"invalid-feedback\">\n" +
                        "                    Iltimos izoh ham kiriting!\n" +
                        "                  </div>\n" +
                        "                </div>\n" +
                        "\n" +
                        "              </div>\n" +
                        "              <div class=\"form-row\">\n" +
                        "                <div class=\"col-md-6 mb-3 md-form\">\n" +
                        "                  <label for=\"picture\">Rasm:</label>\n" +
                        "                  <input type=\"file\" class=\"\" name=\"picture\" value=\"tanlash\" id=\"picture\" required>\n" +
                        "                  <div class=\"invalid-feedback\">\n" +
                        "                    Iltimos rasm tanlang!\n" +
                        "                  </div>\n" +
                        "                </div>\n" +
                        "              </div>\n" +
                        "\n" +
                        "              <button class=\"btn btn-primary btn-sm btn-rounded\" id=\"addMyBook\" type=\"submit\">Saqlash</button>\n" +
                        "            </form>\n" +
                        "          </div>\n" +
                        "        </div>\n" +
                        "\n" +
                        "      </div>\n" +
                        "    </div>" +
                        "<div class=\"modal fade\" id= \"deleteBookModal "+bookId+"\" tabindex= \"-1 \" role= \"dialog \" aria-labelledby= \"exampleModalLabel \" aria-hidden= \"true \">\n" +
                        "          <div class= \"modal-dialog \" role= \"document \">  \n" +
                        "                <div class= \"modal-content \">  \n" +
                        "          <div class=\"modal-header\">\n" +
                        "            <button type=\"button\" class=\"close\" data-dismiss=\"modal\">&times;</button>\n" +
                        "          </div>\n" +
                        "                      <div class= \"modal-body \">  \n" +
                        "                            Siz haqiqatan ham kitobni o'chirmoqchimisiz?  \n" +
                        "                          </div>  \n" +
                        "                      <div class= \"modal-footer \">  \n" +
                        "                            <button type= \"button\" class= \"btn btn-secondary \" data-dismiss= \"modal \">Yo'q</button>\n" +
                        "                            <a href='/actionBook/deleteMyBook/"+parseInt(bookId)+"'>Ha</a>  \n" +
                        "                        <button type= \"button \" id= \"deleteBook "+bookId+"\" data-dismiss= \"modal \" class= \"btn btn-primary \">Ha</button>\n" +
                        "                          </div>  \n" +
                        "                    </div>  \n" +
                        "              </div>  \n" +
                        "        </div>");
                }

            },
            error: function (jqXHR, exception) {
                console.log(jqXHR);
                getErrorMessage(jqXHR, exception);
            }
        })
    });
    // $(".card2-btn").click(function(){
    //     alert($(this).attr("#data-id"));
    //     var bookId = $(this).attr("#data-id");
    //     // console.log("value lik parsesz : " + parseInt($("#bookIdInAppend").val()) );
    //     $(this).find("#deleteBook").data("id",bookId)})
    // console.log("book id = " + bookId);


    });


$("#batafsilKitobIdButton").click(function () {
    console.log("kitob id ning qiymati : " + parseInt($("#id").text()));

    var myBooks = $("#modalbodyid");
    myBooks.empty();
    $.ajax({
            type:'GET',
            url:"/actionBook/batafsilKitobId/" + parseInt($("#id").text()),
            async:false,
            success:function (data){
                // $("#id").empty();
                console.log(data);
                    myBooks.append("<div class='img'>\n" +
                        '<img  src="data:image/jpg;base64,'+data.picture+'"/>'+
                        "            </div>\n" +
                        "<p class='title' style='text-transform: uppercase'> " + data.name + "</p>"           +
                        "<h5 class='copy'> " +'+'+ data.username + "</h5>"+
                        "<h5 class='copy' style='text-transform: capitalize'> " + data.author + "</h5>"+
                        "<h5 class='copy'> " + data.language + "</h5>"+
                        "<h5 class='copy'> " + data.comment + "</h5>"+
                        "<h5 class='copy'> " + data.region + ' ' + data.district +"</h5>");

                }
            })

        });




    $("#defaultAllBooks").click(function () {
        $.ajax({
            type:'GET',
            url:"/actionBook/allBooks",
            async:false,
            success:function (data){
                var div1 = document.getElementById('defaultAllBooks');
                var div2 = document.getElementById('myBooks');
                var div3 = document.getElementById('mySearchedBooks');
                div1.style.display = 'contents';
                div2.style.display = 'none';
                div3.style.display = 'none';

            }                           })
    });

    function onLoadPage()
{
    $('#addAddressModal').modal('show');
};







<!--addMyBook-->








$(document).ready(function(){
    var input = document.getElementById('searchBook');
    input.addEventListener('input', searchBook);
    var bookId;
    function searchBook(){
        jQuery.ajax({
            url:"/actionBook/book/"+$("#searchBook").val(),
            type: "get",
            processData: false,
            contentType: false,
            success: function (data) {
                var div1 = document.getElementById('carouselid');
                var div2 = document.getElementById('myBooks');
                var div3 = document.getElementById('mySearchedBooks');
                var div4 = document.getElementById('pstyle2');
                var div5 = document.getElementById('pstyle');
                div1.style.display = 'none';
                div2.style.display = 'none';
                div3.style.display = 'contents';
                div4.style.display = 'contents';
                div5.style.display = 'none';
                var myBooks = $("#mySearchedBooks");
                $("#allBooks").empty();
                $("#myBooks").empty();
                $("#mySearchedBooks").empty();
                for (i = 0; i < data.length; i++) {
                    bookId = data[i].bookId;
                    myBooks.append("<div class='card'>" +
                        "<div class='img'>"+
                        '<img  src="data:image/jpg;base64,'+data[i].picture+'"/>'+"</div>"+
                        "<div class='content'>" +
                        "<p class='title' style='text-transform: uppercase'> " + data[i].name + "</p>"           +
                        // "<input type='button' class='card1-btn' value='O`zgartirish'>"+
                        "<input type='button'  class='card1-btn' data-id='"+data[i].bookid+"'  value='Batafsil' data-toggle='modal' data-target='#batafsilSearch "+bookId+"' >"+
                        "</div></div>" +
                        "<div class=\"modal fade\" id= \"batafsilSearch "+bookId+"\" tabindex= \"-1 \" role= \"dialog \" aria-labelledby= \"exampleModalLabel \" aria-hidden= \"true \">\n" +
                        "          <div class= \"modal-dialog \" role= \"document \">  \n" +
                        "                <div class= \"modal-content \">  \n" +
                        "                      <div class= \"modal-header \">  \n" +
                        "<p class='title' style='text-transform: uppercase ; text-align: center;'> " + data[i].name + "</p>"           +
                        "<button type=\"button\" class=\"close\"  data-dismiss=\"modal\" aria-label=\"Close\">"+
                        "<span aria-hidden=\"true\">&times;</span> \n" +
                        "</button> \n"+
                        "                          </div>  \n" +
                        "                      <div class= \"modal-body \">  \n" +
                        "<div class='img'>\n" +
                        '<img  src="data:image/jpg;base64,'+data[i].picture+'"/>'+
                        "            </div>\n" +
                        "<p> " +'+'+ data[i].username + "</>"+
                        "<p style='text-transform: capitalize'> " + data[i].author + "</p>"+
                        "<p> " + data[i].language + "</p>"+
                        "<p> " + data[i].comment + "</p>"+
                        "<p> " + data[i].region + ' ' + data[i].district +"</p>"+
                        "                          </div>  \n" +
                        "                    </div>  \n" +
                        "              </div>  \n" +
                        "        </div>");
                }
            }
        });
    }});

//
$(document).ready(function(){
    $("#addMyBook").click(function(){
        var fd = new FormData();
        var files = $('#picture')[0].files[0];
        fd.append('file',files);
        fd.append('name',$("#name").val());
        fd.append('author',$("#author").val());
        fd.append('language',parseInt($("#language").val()));
        fd.append('comment',$("#comment").val());
        if ($("#modalFormForValidate")) {
            console.log($("#modalFormForValidate"))
        }
        console.log($("#name").val());
        $.ajax({
            url:"/actionBook/addBook",
            type: "POST",
            data: fd,
            processData: false,
            contentType: false,
            success: function (result) {
                $('#modalFormForValidate')[0].reset();
                setTimeout(function(){// wait for 4 secs(2)
                    location.reload(); // then reload the page.(3)
                }, 4000);
            }
        });
    });
});

function checkAddressExist() {
    $.ajax(
        {
            type:"get",
            url: "/actionBook/region",
            success:function(response)
            {
                $("#addBook").modal('show');
                console.log(response);
            },error: function (jqXHR, exception) {
                console.log(jqXHR);
                errorGettingAddress(jqXHR, exception);
            }
        });
}

function errorGettingAddress(jqXHR, exception) {
    var msg = '';
    if (jqXHR.status === 0) {
        msg = 'Not connect.\n Verify Network.';
    } else if (jqXHR.status == 404) {
        msg = 'Requested page not found. [404]';
    }else if (jqXHR.status == null) {
        console.log(jqXHR.status);
        msg = 'Iltimos avval manzilni kiriting!';
    } else if (jqXHR.status == 500) {
        msg = 'Iltimos avval manzilni kiriting!';
    } else if (exception === 'parsererror') {
        msg = 'Requested JSON parse failed.';
    } else if (exception === 'timeout') {
        msg = 'Time out error.';
    } else if (exception === 'abort') {
        msg = 'Ajax request aborted.';
    } else {
        msg = 'Uncaught Error.\n' + jqXHR.responseText;
    }
    $('#checkAddress').html(msg);
}

(function() {
    'use strict';
    window.addEventListener('load', function() {
// Fetch all the forms we want to apply custom Bootstrap validation styles to
        var forms = document.getElementsByClassName('needs-validation');
// Loop over them and prevent submission
        var validation = Array.prototype.filter.call(forms, function(form) {
            form.addEventListener('submit', function(event) {
                if (form.checkValidity() === false) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                form.classList.add('was-validated');
            }, false);
        });
    }, false);
})();

