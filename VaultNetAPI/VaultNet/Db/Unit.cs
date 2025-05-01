using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace VaultNet.Db
{
    public class Unit
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int UnitId { get; set; }

        [Required]
        [StringLength(50)]
        public required string Name { get; set; }

        [Required,StringLength(10)]
        public string Symbol { get; set; }

        public virtual ICollection<Product> Products { get; set; }
    }
}
