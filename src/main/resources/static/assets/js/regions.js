// $(document).ready(
//     function () {
//         $.ajax({
//             url: "/regions",
//             type: "GET",
//             success: function (regions) {
//                 // $('#region').empty();
//                 $.each(regions, function (i, region) {
//                     $('#region').append($("<option/>", {
//                         value: region.id,
//                         text: region.name
//                     }));
//                 });
//             },
//             error: function (jqXHR, exception) {
//                 console.log(error);
//             }
//         })
//         $('#region').change(function () {
//             var id = this.value;
//             console.log(id)
//             getDistrict(id)
//         })
//     })
//
// function getDistrict(id) {
//     $.ajax({
//         url: "/districts",
//         data: {regionId: id},
//         type: "GET",
//         success: function (districts) {
//             $('#district').empty();
//             $.each(districts, function (i, district) {
//                 $('#district').append($("<option/>", {
//                     value: district.id,
//                     text: district.name
//                 }));
//             });
//         },
//         error: function (jqXHR, exception) {
//             console.log(error);
//         }
//     })
// }