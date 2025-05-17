using Microsoft.AspNetCore.Mvc;
using VaultNet.Db;
using static System.Net.Mime.MediaTypeNames;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace VaultNet.Controllers
{
    [Route("api/bussiness")]
    [ApiController]
    public class BussinessController : ControllerBase
    {
        VaultNetContext _context;


        public BussinessController(VaultNetContext context) {
            _context = context;
   
        }

        [HttpGet("{id}")]
        public string Get(int id)
        {
            return "value";
        }

        public class BussinessRequest
        {
            public required string Name { get; set; }
            public IFormFile? Logo { get; set; }
        }

         

        // POST api/<BussinessController>
        [HttpPost]
        public async Task<IResult> Post([FromForm] BussinessRequest newBussiness, [FromForm] int userId)
        {
            // Validación básica
            if (newBussiness == null || string.IsNullOrWhiteSpace(newBussiness.Name))
            {
                return Results.BadRequest("Invalid business data");
            }

            string logosFolder = Path.Combine("wwwroot", "resources", "Images", "Logos");
            Directory.CreateDirectory(logosFolder); // CreateDirectory no hace nada si ya existe

            string route = "";
            if (newBussiness.Logo != null)
            {
                // Validar tipo y tamaño del archivo
                var allowedExtensions = new[] { ".jpg", ".jpeg", ".png", ".gif" };
                var extension = Path.GetExtension(newBussiness.Logo.FileName).ToLowerInvariant();

                if (!allowedExtensions.Contains(extension))
                {
                    return Results.BadRequest("Invalid file type");
                }

                if (newBussiness.Logo.Length > 5 * 1024 * 1024) // 5MB
                {
                    return Results.BadRequest("File too large");
                }

                // Generar nombre único para el archivo
                var fileName = $"{Guid.NewGuid()}{extension}";
                route = Path.Combine(logosFolder, fileName);

                try
                {
                    using var stream = new FileStream(route, FileMode.Create);
                    await newBussiness.Logo.CopyToAsync(stream);
                }
                catch (Exception ex)
                {
                    return Results.Problem($"Error saving file: {ex.Message}");
                }

                // Guardar ruta relativa
                route = Path.Combine("resources", "Images", "Logos", fileName);
            }

            try
            {
                var business = new Bussiness
                {
                    Name = newBussiness.Name,
                    LogoUrl = string.IsNullOrEmpty(route) ? null : $"/{route.Replace('\\', '/')}",
                };

                await _context.AddAsync(business);
                await _context.SaveChangesAsync();

                return Results.Created($"/business/{business.BussinessId}", business);
            }
            catch (Exception ex)
            {
                // Eliminar el archivo si hubo error en la base de datos
                if (!string.IsNullOrEmpty(route) && System.IO.File.Exists(route))
                {
                    System.IO.File.Delete(route);
                }
                return Results.Problem($"Error creating business: {ex.Message}");
            }
        }

        // PUT api/<BussinessController>/5
        [HttpPut("{id}")]
        public void Put(int id, [FromBody] string value)
        {
        }

        // DELETE api/<BussinessController>/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
        }
    }
}
