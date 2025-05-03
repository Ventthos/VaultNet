using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using VaultNet.Db;

namespace VaultNet.Db
{
    public class Bussiness
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int BussinessId { get; set; }

        [Required]
        [StringLength(100)]
        public required string Name { get; set; }

        [Required]
        [StringLength(500)]
        public string? LogoUrl { get; set; }

        public virtual ICollection<UserBussiness> Users { get; set; } = new HashSet<UserBussiness>();

        public virtual ICollection<Product> Products { get; set; } = new HashSet<Product>();

        public virtual ICollection<Unit> Units { get; set; } = new HashSet<Unit>();

        public virtual ICollection<Category> Categories { get; set; } = new HashSet<Category>();

        
    }
}
