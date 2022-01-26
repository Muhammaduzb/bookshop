$(document).ready(function () {
    var userId = '';
    var userForm = $('#userForm');

    var userTable = $('#getUsers').DataTable({
        ajax: {
            url: "/cabinet/users/list",
            type: "GET",
            dataSrc: ''
        },
        columns: [
            {title: "id", data: "id"},
            {title: "name", data: "username"},
            {title: "Locked", data: "accountNonLocked"},
            {title: "enabled", data: "enabled"},
            {
                title: "Operaton", data: "id", render: function (data, type, row) {
                    return '<div class="row">\n' +
                        '    <div class="col mt-1 mb-1">\n' +
                        '        <button  type="button" class="btn btn-primary text-white btn-block btn-edit"><i class="fas fa-edit"></i> Edit</button>\n' +
                        '    </div>\n' +
                        '    <div class="col mt-1 mb-1">\n' +
                        '        <button data-toggle="modal" data-target="#modalDelete" class=" text-white btn btn-danger btn-block btn-delete" style="color: darkred"><i class="fab fa-bitbucket"> </i> Delete</button>\n' +
                        '    </div>\n' +
                        '</div>\n';
                }
            }
        ]
    });

    $.validate({
        form: '#userForm',
        errorMessageClass: 'text-danger',
        onSuccess: function ($form) {
            console.log(JSON.stringify($form.serializeObject()));
            userForm.serialize();
            $.ajax({
                url: "/cabinet/user/edit/" + userId,
                contentType: "application/json; charset=utf-8",
                method: 'PUT',
                dataType: 'json',
                data: JSON.stringify($form.serializeObject()),
                success: function (data) {
                    userTable.ajax.reload();
                    $('#userModal').modal('toggle');
                    $.toast({
                        heading: 'Information',
                        text: data.message,
                        icon: 'success',
                        position: 'top-right'
                    });
                },
                error: function () {
                    console.log(JSON.stringify($form.serializeObject()));
                    $.toast({
                        heading: 'information',
                        text: "Error",
                        icon: 'error',
                        position: 'top-right'
                    })
                }
            });
            return false;
        }
    });

    $(userTable.table().body()).on('click', '.btn-edit', function () {

        $('#userForm')[0].reset();
        $("#userForm input").removeAttr('checked');

        var data = userTable.row($(this).parents('tr')).data();
        userId = data.id;
        // console.log(userId);
        $('#userModal').modal('toggle');

        Object.keys(data).map(function (key) {
            $('[name=' + key + ']').val(data[key]);
        });

        // console.log(data.roles);

        $.each(data.roles, function (i, role) {
            $('[name=' + camelCase(role.name.toLowerCase()) + ']').attr('checked', 'checked').val(true);
        });

        $('#accountNonLocked').prop('checked', data.accountNonLocked);


    });

    $(userTable.table().body()).on('click', '.btn-delete', function () {
        var data = userTable.row($(this).parents('tr')).data();
        userId = data.id;
        $('#userDeleteModal').modal('toggle');
    });

    $('#userDelBtn').click(function () {
        $.ajax({
            url: "/cabinet/user/delete/" + userId,
            contentType: "application/json; charset=utf-8",
            method: 'DELETE',
            dataType: 'json',
            success: function (data) {
                $.toast({
                    heading: 'Information',
                    text: 'Successfully deleted.',
                    icon: 'success',
                    position: 'top-right'
                });
            },
            error: function () {
                $.toast({
                    heading: 'information',
                    text: 'Error in deleting.',
                    icon: 'error',
                    position: 'top-right'
                })
            }
        }).always(function () {
            userTable.ajax.reload();
            $('#userDeleteModal').modal('toggle');
        });
    });

    $("input[type=checkbox]").change(function () {
        $(this).val($(this).prop('checked'));
    });

    $.fn.serializeObject = function () {
        var o = {};
        //    var a = this.serializeArray();
        $(this).find('input[type="hidden"], input[type="text"], input[type="password"], input[type="checkbox"]:checked, input[type="radio"]:checked, select, textarea').each(function () {
            if ($(this).attr('type') == 'hidden') { //if checkbox is checked do not take the hidden field
                var $parent = $(this).parent();
                var $chb = $parent.find('input[type="checkbox"][name="' + this.name.replace(/\[/g, '\[').replace(/\]/g, '\]') + '"]');
                if ($chb != null) {
                    if ($chb.prop('checked')) return;
                }
            }
            if (this.name === null || this.name === undefined || this.name === '')
                return;
            var elemValue = null;
            if ($(this).is('select'))
                elemValue = $(this).find('option:selected').val();
            else elemValue = this.value;
            if (o[this.name] !== undefined) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(elemValue || '');
            } else {
                o[this.name] = elemValue || '';
            }
        });
        return o;
    }

});

var camelCase = (function () {
    var DEFAULT_REGEX = /[-_]+(.)?/g;

    function toUpper(match, group1) {
        return group1 ? group1.toUpperCase() : '';
    }

    return function (str, delimiters) {
        return str.replace(delimiters ? new RegExp('[' + delimiters + ']+(.)', 'g') : DEFAULT_REGEX, toUpper);
    };
})();