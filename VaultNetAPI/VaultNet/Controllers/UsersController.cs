using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using MySqlX.XDevAPI.Common;
using System.ComponentModel.DataAnnotations;
using System.Threading.Tasks;
using VaultNet.Db;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace VaultNet.Controllers
{
    [Route("api/users")]
    [ApiController]
    public class UsersController : ControllerBase
    {
        private VaultNetContext _context;
        public UsersController(VaultNetContext context)
        {
            _context = context;
        }

        // POST api/users
        [HttpPost("register")]
        public async Task<IResult> Post([FromBody] User newUser)
        {
            try
            {
                // Vemos si el nuevo usuario ya existe
                var exists = await _context.Users.AnyAsync(u => u.Email == newUser.Email);
                if (exists)
                    return Results.BadRequest("Ese correo ya está registrado.");

                // Si no existe lo agregamos
                _context.Users.Add(newUser);
                await _context.SaveChangesAsync();
                return Results.Created("", newUser);
            }
            catch
            {
                // Si hay algun error, mandamos el error.
                return Results.Problem("Ocurrió un error inesperado.");
            }

        }

        public class LoginRequest()
        {
            public required string Email { get; set; }
            public required string Password { get; set; }
        }

        public class UserResponse()
        {
            public int IdUser { get; set; }
            public required string Name { get; set; }

            public required string PaternalLastName { get; set; }

            public string? MaternalLastName { get; set; }

            public required string Email { get; set; }
        }

        [HttpGet("{id}")]
        public async Task<IResult> GetUser(int id)
        {

            User? user = await _context.Users.FindAsync(id);

            if (user == null)
            {
                return Results.NotFound("El usuario no se encuentra registrado.");
            }

            return Results.Ok(new UserResponse
            {
                IdUser = user.UserId,
                Name = user.Name,
                PaternalLastName = user.PaternalLastName,
                MaternalLastName = user.MaternalLastName,
                Email = user.Email
            });
        }

        public class IdsRequest
        {
            public int[] Ids { get; set; } = [];
        }

        [HttpPost]
        public async Task<IResult> GetUsers([FromBody] IdsRequest ids)
        {

            List<User> users = await _context.Users.Where(u=> ids.Ids.Contains(u.UserId)).ToListAsync();

            if(users.Count == 0)
            {
                return Results.NotFound("No se encontraron los usuarios.");
            }

            HashSet<UserResponse> usersSet = new HashSet<UserResponse>();

            foreach(var user in users)
            {
                usersSet.Add(new UserResponse
                {
                    IdUser = user.UserId,
                    Name = user.Name,
                    PaternalLastName = user.PaternalLastName,
                    MaternalLastName = user.MaternalLastName,
                    Email = user.Email
                });
            }

            return Results.Ok(usersSet);
        }

        [HttpPost("login")]
        public async Task<IResult> LogIn([FromBody] LoginRequest data)
        {
            User? user = await _context.Users.SingleOrDefaultAsync(u => u.Email == data.Email);

            if (user == null)
            {
                return Results.NotFound("El usuario no se encuentra registrado.");
            }

            if (user.Password != data.Password)
            {
                return Results.BadRequest("La contraseña no coincide con el correo proporcionado");
            }

            return Results.Ok(new UserResponse
            {
                IdUser = user.UserId,
                Name = user.Name,
                PaternalLastName = user.PaternalLastName,
                MaternalLastName = user.MaternalLastName,
                Email = user.Email
            });
        }
    }
}
