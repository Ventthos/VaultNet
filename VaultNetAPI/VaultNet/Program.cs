using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using VaultNet.Db;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.

builder.Services.AddDbContext<VaultNetContext>(options=>{
    var serverVersion = new MySqlServerVersion(new Version(8, 0, 32));
    options.UseMySql(builder.Configuration.GetConnectionString("VaultNetConnection"), serverVersion);
});

builder.Services.AddControllers();

builder.Services.Configure<ApiBehaviorOptions>(options =>
{
    options.InvalidModelStateResponseFactory = context =>
    {
        var errors = context.ModelState
            .Where(e => e.Value?.Errors.Count > 0)
            .ToDictionary(
                kvp => kvp.Key,
                kvp => kvp.Value!.Errors.Select(e => e.ErrorMessage).ToArray()
            );

        var customResponse = new
        {
            mensaje = "Hubo un problema con los datos enviados.",
            errores = errors
        };

        return new BadRequestObjectResult(customResponse);
    };
});

var app = builder.Build();


using (var scope = app.Services.CreateScope())
{
    var dataContext = scope.ServiceProvider.GetRequiredService<VaultNetContext>();
    dataContext.Database.Migrate();
}



// Configure the HTTP request pipeline.

app.UseAuthorization();

app.MapControllers();

app.Run();
