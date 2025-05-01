using ChargedChat.Db;
using Microsoft.AspNetCore.Authentication;
using Microsoft.EntityFrameworkCore;

namespace VaultNet.Db
{
    public class VaultNetContext: DbContext
    {
        public VaultNetContext(DbContextOptions<VaultNetContext> options) : base(options)
        {

        }

        public DbSet<User> Users { get; set; }
        public DbSet<Bussiness> Bussiness { get; set; }
        public DbSet<Product> Products { get; set; }
        public DbSet<Category> Categories { get; set; }
        public DbSet<Unit> Units { get; set; }
        public DbSet<Snapshot> Snapshots { get; set; }
    }
}
