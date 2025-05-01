using Microsoft.AspNetCore.Authentication;
using Microsoft.EntityFrameworkCore;
using VaultNet.Db;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.

builder.Services.AddDbContext<VaultNetContext>(options=>{
    var serverVersion = new MySqlServerVersion(new Version(8, 0, 32));
    options.UseMySql(builder.Configuration.GetConnectionString("VaultNetConnection"), serverVersion);
});

builder.Services.AddControllers();

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
